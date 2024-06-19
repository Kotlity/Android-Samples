package com.kotlity.workmanager.ui.screens

import android.app.DownloadManager
import android.content.Context
import android.content.IntentFilter
import android.os.Build
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PauseCircle
import androidx.compose.material.icons.filled.StopCircle
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.work.WorkManager
import com.kotlity.workmanager.R
import com.kotlity.workmanager.receivers.DownloadFileBroadcastReceiver
import com.kotlity.workmanager.repositories.launcher.Launcher
import com.kotlity.workmanager.ui.composables.AudioButton
import com.kotlity.workmanager.ui.composables.AudioImage
import com.kotlity.workmanager.ui.composables.SiteLauncherButton
import com.kotlity.workmanager.ui.composables.SiteTextField
import com.kotlity.workmanager.ui.events.DownloadFileEvent
import com.kotlity.workmanager.ui.events.MediaAudioPlayerEvent
import com.kotlity.workmanager.ui.events.OnDownloadFileFinished
import com.kotlity.workmanager.ui.states.AudioState
import com.kotlity.workmanager.ui.states.DownloadFileState
import com.kotlity.workmanager.ui.states.ValidatorState
import com.kotlity.workmanager.utils.Constants.FloatConstants._06f
import com.kotlity.workmanager.utils.Constants.FloatConstants._08f
import com.kotlity.workmanager.utils.Constants.TextSizeConstants._18sp
import com.kotlity.workmanager.utils.asUiText
import com.kotlity.workmanager.utils.onWorkInfoStatus
import com.kotlity.workmanager.utils.retrieveFileImage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    launcher: Launcher,
    workManager: WorkManager,
    downloadFileState: DownloadFileState,
    audioState: AudioState,
    validatorState: ValidatorState,
    onDownloadFileFinishedFlow: Flow<OnDownloadFileFinished>,
    onDownloadFileEvent: (DownloadFileEvent) -> Unit,
    onMediaAudioPlayerEvent: (MediaAudioPlayerEvent) -> Unit
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val workInfo = downloadFileState.workRequestUUID?.let { workManager.getWorkInfoByIdLiveData(it).observeAsState().value }
    val downloadFileBroadcastReceiver by rememberUpdatedState(newValue = DownloadFileBroadcastReceiver { fileUri, fileName, fileImage ->
        onMediaAudioPlayerEvent(MediaAudioPlayerEvent.OnInitMediaAudioPlayer(fileUri = fileUri))
        onMediaAudioPlayerEvent(MediaAudioPlayerEvent.OnFileNameUpdate(fileName = fileName))
        fileImage?.let { fileImageByteArray ->
            val image = fileImageByteArray.retrieveFileImage()
            onMediaAudioPlayerEvent(MediaAudioPlayerEvent.OnFileImageUpdate(fileImage = image))
        }
    })

    val isShowAudioWidgets = !downloadFileState.isFileDownloading && audioState.name.isNotEmpty()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            SiteTextField(
                modifier = Modifier.fillMaxWidth(_08f),
                siteText = downloadFileState.siteUrl,
                isError = validatorState is ValidatorState.Error,
                supportingText = if (validatorState is ValidatorState.Error) validatorState.errorType.asUiText().asComposeString() else null,
                onSiteTextChange = {
                    onDownloadFileEvent(DownloadFileEvent.OnSiteUrlChange(it))
                }
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen._10dp)))
            SiteLauncherButton(
                modifier = Modifier.fillMaxWidth(_06f),
                isEnabled = validatorState is ValidatorState.Success,
                onButtonClick = {
                    launcher.launch(downloadFileState.siteUrl)
                }
            )
            if (downloadFileState.isFileDownloading) CircularProgressIndicator()
            if (isShowAudioWidgets) {
                AudioImage(
                    modifier = Modifier.size(dimensionResource(id = R.dimen._200dp)),
                    image = audioState.image
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen._3dp)))
                Text(
                    text = audioState.name,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = _18sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen._20dp))
                ) {
                    if (audioState.isAudioPlaying) {
                        AudioButton(
                            modifier = Modifier.size(dimensionResource(id = R.dimen._50dp)),
                            icon = Icons.Default.PauseCircle,
                            onButtonClick = {
                                onMediaAudioPlayerEvent(MediaAudioPlayerEvent.OnPause)
                            }
                        )
                    } else {
                        AudioButton(
                            modifier = Modifier.size(dimensionResource(id = R.dimen._50dp)),
                            onButtonClick = {
                                onMediaAudioPlayerEvent(MediaAudioPlayerEvent.OnPlay)
                            }
                        )
                    }
                    AudioButton(
                        modifier = Modifier.size(dimensionResource(id = R.dimen._50dp)),
                        icon = Icons.Default.StopCircle,
                        color = IconButtonDefaults.outlinedIconButtonColors(containerColor = Color.Red),
                        onButtonClick = {
                            onMediaAudioPlayerEvent(MediaAudioPlayerEvent.OnStop)
                        }
                    )
                }
            }
        }
    }

    LaunchedEffect(
        key1 = workInfo?.state,
        key2 = onDownloadFileFinishedFlow
    ) {
        launch {
            workInfo?.onWorkInfoStatus(onDownloadFileEvent)
        }
        launch {
            onDownloadFileFinishedFlow.collectLatest {
                snackbarHostState.showSnackbar(message = it.result.asString(context))
            }
        }
    }

    DisposableEffect(key1 = context) {
        val downloadFileIntentFilter = IntentFilter().apply {
            addAction(DownloadManager.ACTION_NOTIFICATION_CLICKED)
            addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.registerReceiver(downloadFileBroadcastReceiver, downloadFileIntentFilter, Context.RECEIVER_NOT_EXPORTED)
        } else context.registerReceiver(downloadFileBroadcastReceiver, downloadFileIntentFilter)
        onDispose {
            context.unregisterReceiver(downloadFileBroadcastReceiver)
            onMediaAudioPlayerEvent(MediaAudioPlayerEvent.OnRelease)
        }
    }

}