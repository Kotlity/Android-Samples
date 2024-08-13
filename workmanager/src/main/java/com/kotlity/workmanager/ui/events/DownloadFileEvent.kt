package com.kotlity.workmanager.ui.events

import com.kotlity.workmanager.utils.UiText
import java.util.UUID

sealed interface DownloadFileEvent {
    data class OnSiteUrlChange(val siteUrl: String): DownloadFileEvent
    data class OnWorkRequestUUIDChange(val uuid: UUID): DownloadFileEvent
    data class OnDownloadFileResult(val result: UiText): DownloadFileEvent
    data object OnDownloadingFileProgressBarUpdate: DownloadFileEvent
}