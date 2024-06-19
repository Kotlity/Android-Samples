package com.kotlity.workmanager.ui.events

import com.kotlity.workmanager.utils.UiText

sealed class OnDownloadFileFinished(val result: UiText) {
    class OnFinished(result: UiText): OnDownloadFileFinished(result = result)
}