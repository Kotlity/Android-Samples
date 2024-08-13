package com.kotlity.services.file

import android.content.Context
import java.io.File

class PhotosFileCompressionHelper(private val context: Context): FileCompressionHelper {

    override suspend fun writeBytesToFile(fileName: String, outputBytes: ByteArray): String {
        val compressedPhotoFile = File(context.cacheDir, fileName)
        compressedPhotoFile.writeBytes(outputBytes)
        return compressedPhotoFile.absolutePath
    }
}