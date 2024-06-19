package com.kotlity.workmanager.ui.viewmodels

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlity.workmanager.repositories.media.AudioPlayer
import com.kotlity.workmanager.ui.events.MediaAudioPlayerEvent
import com.kotlity.workmanager.ui.states.AudioState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MediaAudioPlayerViewModel @Inject constructor(private val audioPlayer: AudioPlayer): ViewModel() {

    var audioState by mutableStateOf(AudioState())
        private set

    fun onEvent(mediaAudioPlayerEvent: MediaAudioPlayerEvent) {
        when(mediaAudioPlayerEvent) {
            is MediaAudioPlayerEvent.OnInitMediaAudioPlayer -> onInitMediaAudioPlayer(mediaAudioPlayerEvent.fileUri)
            is MediaAudioPlayerEvent.OnFileNameUpdate -> onFileNameUpdate(mediaAudioPlayerEvent.fileName)
            is MediaAudioPlayerEvent.OnFileImageUpdate -> onFileImageUpdate(mediaAudioPlayerEvent.fileImage)
            MediaAudioPlayerEvent.OnPlay -> onPlay()
            MediaAudioPlayerEvent.OnPause -> onPause()
            MediaAudioPlayerEvent.OnStop -> onStop()
            MediaAudioPlayerEvent.OnRelease -> onRelease()
        }
    }

    private fun onInitMediaAudioPlayer(fileUri: Uri) {
        audioPlayer.init(fileUri = fileUri)
    }

    private fun onFileNameUpdate(fileName: String) {
        audioState = audioState.copy(name = fileName)
    }

    private fun onFileImageUpdate(fileImage: Bitmap?) {
        audioState = audioState.copy(image = fileImage)
    }

    private fun onPlay() {
        viewModelScope.launch {
            audioPlayer.play()
            updateIsAudioPlaying()
        }
    }

    private fun onPause() {
        viewModelScope.launch {
            audioPlayer.pause()
            updateIsAudioPlaying()
        }
    }

    private fun onStop() {
        viewModelScope.launch {
            audioPlayer.stop()
            updateIsAudioPlaying()
        }
    }

    private fun onRelease() {
        viewModelScope.launch {
            audioPlayer.release()
        }
    }

    private fun updateIsAudioPlaying() {
        audioState = audioState.copy(isAudioPlaying = audioPlayer.isAudioPlaying)
    }
}