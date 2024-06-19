package com.kotlity.workmanager.workers

import android.content.Context
import android.media.MediaMetadataRetriever
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.kotlity.workmanager.R
import com.kotlity.workmanager.repositories.file.FileDownloader
import com.kotlity.workmanager.repositories.file.MetadataRetriever
import com.kotlity.workmanager.utils.AppDispatchers
import com.kotlity.workmanager.utils.Constants.DOWNLOAD_FILE_ERROR_KEY
import com.kotlity.workmanager.utils.Constants.DOWNLOAD_FILE_SUCCESS_KEY
import com.kotlity.workmanager.utils.Constants.FILE_URL_KEY
import com.kotlity.workmanager.utils.Constants.GETTING_FILE_URL_ERROR_KEY
import com.kotlity.workmanager.utils.setResult
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.withContext

@HiltWorker
class DownloadMusicWorker @AssistedInject constructor(
    private val fileDownloader: FileDownloader,
    private val appDispatchers: AppDispatchers,
    private val metadataRetriever: MetadataRetriever,
    @Assisted private val applicationContext: Context,
    @Assisted private val workerParameters: WorkerParameters
): CoroutineWorker(
    appContext = applicationContext,
    params = workerParameters
) {
    override suspend fun doWork(): Result {
        return withContext(appDispatchers.ioDispatcher) {
            val fileUrl = workerParameters.inputData.getString(FILE_URL_KEY) ?: return@withContext setResult(success = false, pairs = arrayOf(
                GETTING_FILE_URL_ERROR_KEY to applicationContext.getString(R.string.gettingFileUrlError))
            )
            val fileTitle = metadataRetriever.retrieveData(fileUrl, MediaMetadataRetriever.METADATA_KEY_TITLE) ?: System.currentTimeMillis().toString()
            val isFileSuccessfulDownloaded = fileDownloader.downloadFile(fileUrl, fileTitle)
            if (isFileSuccessfulDownloaded) {
                setResult(success = true, pairs = arrayOf(
                    DOWNLOAD_FILE_SUCCESS_KEY to applicationContext.getString(R.string.fileDownloadComplete)
                ))
            } else {
                setResult(success = false, pairs = arrayOf(
                    DOWNLOAD_FILE_ERROR_KEY to applicationContext.getString(R.string.downloadFileError))
                )
            }
        }
    }
}