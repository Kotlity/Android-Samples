package com.kotlity.workmanager.repositories.media

import android.net.Uri

interface AudioPlayer {

    val isAudioPlaying: Boolean

    fun init(fileUri: Uri)
    fun play()
    fun pause()
    fun stop()
    suspend fun release()
}