package com.kotlity.workmanager.ui.states

import android.graphics.Bitmap

data class AudioState(
    val name: String = "",
    val image: Bitmap? = null,
    val isAudioPlaying: Boolean = false
)