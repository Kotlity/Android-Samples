package com.kotlity.workmanager.ui.states

import com.kotlity.workmanager.utils.UiText
import java.util.UUID

data class DownloadFileState(
    val siteUrl: String = "",
    val siteUrlValidationError: UiText? = null,
    val workRequestUUID: UUID? = null,
    val isFileDownloading: Boolean = false
)