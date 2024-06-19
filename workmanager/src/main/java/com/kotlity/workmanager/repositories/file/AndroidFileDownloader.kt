package com.kotlity.workmanager.repositories.file

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import com.kotlity.workmanager.R
import com.kotlity.workmanager.utils.CustomException
import javax.inject.Inject

class AndroidFileDownloader @Inject constructor(
    private val context: Context,
    private val downloadManager: DownloadManager
): FileDownloader {

    override suspend fun downloadFile(fileUrl: String, fileName: String): Boolean {
        val downloadRequest = DownloadManager.Request(Uri.parse(fileUrl))
            .setMimeType("audio/*")
            .setTitle(context.getString(R.string.downloadFileTitle, arrayOf(fileName)))
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
        return try {
            downloadManager.enqueue(downloadRequest)
            true
        } catch (e: Exception) {
            e.localizedMessage?.let { CustomException(it) }
            false
        }
    }
}