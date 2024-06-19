package com.kotlity.workmanager.ui.events

import android.graphics.Bitmap
import android.net.Uri

sealed interface MediaAudioPlayerEvent {
    data class OnInitMediaAudioPlayer(val fileUri: Uri): MediaAudioPlayerEvent
    data class OnFileNameUpdate(val fileName: String): MediaAudioPlayerEvent
    data class OnFileImageUpdate(val fileImage: Bitmap?): MediaAudioPlayerEvent
    data object OnPlay: MediaAudioPlayerEvent
    data object OnPause: MediaAudioPlayerEvent
    data object OnStop: MediaAudioPlayerEvent
    data object OnRelease: MediaAudioPlayerEvent
}