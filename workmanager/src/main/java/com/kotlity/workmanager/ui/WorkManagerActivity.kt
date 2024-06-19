package com.kotlity.workmanager.ui

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.work.Constraints
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.kotlity.workmanager.repositories.launcher.Launcher
import com.kotlity.workmanager.repositories.launcher.SiteLauncher
import com.kotlity.workmanager.ui.events.DownloadFileEvent
import com.kotlity.workmanager.ui.screens.MainScreen
import com.kotlity.workmanager.ui.theme.WorkManagerTheme
import com.kotlity.workmanager.ui.viewmodels.DownloadFileViewModel
import com.kotlity.workmanager.ui.viewmodels.MediaAudioPlayerViewModel
import com.kotlity.workmanager.utils.Constants.FILE_URL_KEY
import com.kotlity.workmanager.workers.DownloadMusicWorker
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WorkManagerActivity: ComponentActivity() {

    private val launcher: Launcher = SiteLauncher(this)

    @Inject
    lateinit var workManager: WorkManager

    private val downloadFileViewModel by viewModels<DownloadFileViewModel>()
    private val mediaAudioPlayerViewModel by viewModels<MediaAudioPlayerViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WorkManagerTheme {
                MainScreen(
                    launcher = launcher,
                    workManager = workManager,
                    downloadFileState = downloadFileViewModel.downloadFileState,
                    audioState = mediaAudioPlayerViewModel.audioState,
                    validatorState = downloadFileViewModel.siteUrlLinkValidator,
                    onDownloadFileFinishedFlow = downloadFileViewModel.onDownloadFileFinishedFlow,
                    onDownloadFileEvent = downloadFileViewModel::onEvent,
                    onMediaAudioPlayerEvent = mediaAudioPlayerViewModel::onEvent
                )
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val fileUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(Intent.EXTRA_STREAM, Uri::class.java)
        } else {
            intent.getParcelableExtra(Intent.EXTRA_STREAM)
        } ?: return
        val workRequest = OneTimeWorkRequestBuilder<DownloadMusicWorker>()
            .setInputData(workDataOf(FILE_URL_KEY to fileUri.toString()))
            .setConstraints(Constraints(requiresStorageNotLow = true))
            .build()
        downloadFileViewModel.onEvent(DownloadFileEvent.OnWorkRequestUUIDChange(uuid = workRequest.id))
        workManager.enqueue(workRequest)
    }
}