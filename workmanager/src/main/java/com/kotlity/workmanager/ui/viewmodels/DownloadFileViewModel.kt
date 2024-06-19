package com.kotlity.workmanager.ui.viewmodels

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlity.workmanager.repositories.validator.TextValidator
import com.kotlity.workmanager.ui.events.DownloadFileEvent
import com.kotlity.workmanager.ui.events.OnDownloadFileFinished
import com.kotlity.workmanager.ui.states.DownloadFileState
import com.kotlity.workmanager.ui.states.ValidatorState
import com.kotlity.workmanager.utils.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class DownloadFileViewModel @Inject constructor(private val textValidator: TextValidator): ViewModel() {

    var downloadFileState by mutableStateOf(DownloadFileState())
        private set

    var siteUrlLinkValidator by derivedStateOf {
        mutableStateOf<ValidatorState>(ValidatorState.Initial)
    }.value
        private set

    private val onDownloadFileFinishedChannel = Channel<OnDownloadFileFinished>()
    val onDownloadFileFinishedFlow = onDownloadFileFinishedChannel.receiveAsFlow()

    fun onEvent(downloadFileEvent: DownloadFileEvent) {
        when(downloadFileEvent) {
            is DownloadFileEvent.OnSiteUrlChange -> onSiteUrlChange(downloadFileEvent.siteUrl)
            is DownloadFileEvent.OnWorkRequestUUIDChange -> onWorkRequestUUIDChange(downloadFileEvent.uuid)
            is DownloadFileEvent.OnDownloadFileResult -> onDownloadFileFinished(downloadFileEvent.result)
            DownloadFileEvent.OnDownloadingFileProgressBarUpdate -> onDownloadingFileProgressBarUpdate()
        }
    }

    private fun onSiteUrlChange(siteUrl: String) {
        downloadFileState = downloadFileState.copy(siteUrl = siteUrl)
        siteUrlLinkValidator = textValidator.validateText(text = siteUrl)
    }

    private fun onWorkRequestUUIDChange(uuid: UUID) {
        downloadFileState = downloadFileState.copy(workRequestUUID = uuid)
    }

    private fun onDownloadingFileProgressBarUpdate() {
        val isFileDownloading = downloadFileState.isFileDownloading
        downloadFileState = downloadFileState.copy(isFileDownloading = !isFileDownloading)
    }

    private fun onDownloadFileFinished(result: UiText) {
        viewModelScope.launch {
            onDownloadFileFinishedChannel.send(OnDownloadFileFinished.OnFinished(result = result))
        }
    }
}