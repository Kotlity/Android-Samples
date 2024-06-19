package com.kotlity.workmanager.receivers

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaMetadataRetriever
import android.net.Uri
import com.kotlity.workmanager.repositories.file.MetadataRetriever
import com.kotlity.workmanager.utils.Constants.DEFAULT_DOWNLOAD_FILE_ID
import com.kotlity.workmanager.utils.CustomException
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DownloadFileBroadcastReceiver(private val onDownloadFileSuccessful: (fileUri: Uri, fileName: String, fileImage: ByteArray?) -> Unit): BroadcastReceiver() {

    @Inject
    lateinit var downloadManager: DownloadManager
    @Inject
    lateinit var metadataRetriever: MetadataRetriever

    override fun onReceive(context: Context, intent: Intent) {
        when(intent.action) {
            DownloadManager.ACTION_NOTIFICATION_CLICKED -> {
                val downloadsIntent = Intent(DownloadManager.ACTION_VIEW_DOWNLOADS)
                context.startActivity(downloadsIntent)
            }
            DownloadManager.ACTION_DOWNLOAD_COMPLETE -> {
                val downloadFileId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, DEFAULT_DOWNLOAD_FILE_ID)
                if (downloadFileId != DEFAULT_DOWNLOAD_FILE_ID) {
                    val query = DownloadManager.Query().apply { setFilterById(downloadFileId) }
                    val cursor = downloadManager.query(query)
                    if (cursor.moveToFirst()) {
                        try {
                            val columnStatusIndex = cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_STATUS)
                            if (DownloadManager.STATUS_SUCCESSFUL == cursor.getInt(columnStatusIndex)) {
                                val columnFileUriIndex = cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_LOCAL_URI)
                                val columnFileUriString = cursor.getString(columnFileUriIndex)
                                val fileUri = Uri.parse(columnFileUriString)
                                val fileName = metadataRetriever.retrieveData(columnFileUriString, MediaMetadataRetriever.METADATA_KEY_TITLE) ?: System.currentTimeMillis().toString()
                                val fileByteArrayImage = metadataRetriever.retrieveImageByteArray(columnFileUriString)
                                onDownloadFileSuccessful(fileUri, fileName, fileByteArrayImage)
                            }
                        } catch (e: Exception) {
                            e.localizedMessage?.let { CustomException(it) }
                        }
                    }
                }
            }
        }
    }
}