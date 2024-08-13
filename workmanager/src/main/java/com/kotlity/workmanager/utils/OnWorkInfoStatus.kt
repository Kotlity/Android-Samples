package com.kotlity.workmanager.utils

import androidx.work.WorkInfo
import com.kotlity.workmanager.ui.events.DownloadFileEvent
import com.kotlity.workmanager.utils.Constants.DOWNLOAD_FILE_ERROR_KEY
import com.kotlity.workmanager.utils.Constants.DOWNLOAD_FILE_SUCCESS_KEY
import com.kotlity.workmanager.utils.Constants.GETTING_FILE_URL_ERROR_KEY

fun WorkInfo.onWorkInfoStatus(onEvent: (DownloadFileEvent) -> Unit) {
    when(state) {
        WorkInfo.State.RUNNING -> {
            onEvent(DownloadFileEvent.OnDownloadingFileProgressBarUpdate)
        }
        WorkInfo.State.SUCCEEDED -> {
            onEvent(DownloadFileEvent.OnDownloadingFileProgressBarUpdate)
            val successResultMessage = outputData.getString(DOWNLOAD_FILE_SUCCESS_KEY)
            onEvent(DownloadFileEvent.OnDownloadFileResult(result = UiText.DynamicString(value = successResultMessage!!)))

        }
        WorkInfo.State.FAILED -> {
            onEvent(DownloadFileEvent.OnDownloadingFileProgressBarUpdate)
            val errorResultMessage = outputData.getString(GETTING_FILE_URL_ERROR_KEY) ?: outputData.getString(DOWNLOAD_FILE_ERROR_KEY)
            onEvent(DownloadFileEvent.OnDownloadFileResult(result = UiText.DynamicString(value = errorResultMessage!!)))
        }
        else -> Unit
    }
}