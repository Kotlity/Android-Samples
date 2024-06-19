package com.kotlity.workmanager.repositories.file

import android.content.Context
import android.media.MediaMetadataRetriever
import android.net.Uri
import com.kotlity.workmanager.utils.CustomException
import javax.inject.Inject

private const val READ_ONLY = "r"

class AudioMetadataRetriever @Inject constructor(
    private val context: Context,
    private val mediaMetadataRetriever: MediaMetadataRetriever
): MetadataRetriever {

    override fun retrieveData(fileUrl: String, metadataKey: Int): String? {
        return try {
            context.contentResolver.openFileDescriptor(Uri.parse(fileUrl), READ_ONLY)?.use { parcelFileDescriptor ->
                mediaMetadataRetriever.setDataSource(parcelFileDescriptor.fileDescriptor)
            }
            return mediaMetadataRetriever.extractMetadata(metadataKey)
        } catch (e: Exception) {
            e.localizedMessage?.let { CustomException(it) }
            null
        }
        finally {
            mediaMetadataRetriever.release()
        }
    }

    override fun retrieveImageByteArray(fileUrl: String): ByteArray? {
        return try {
            mediaMetadataRetriever.embeddedPicture
        } catch (e: Exception) {
            e.localizedMessage?.let { CustomException(it) }
            null
        }
        finally {
            mediaMetadataRetriever.release()
        }
    }
}