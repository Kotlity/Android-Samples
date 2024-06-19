package com.kotlity.workmanager.repositories.media

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import com.kotlity.workmanager.utils.AppDispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val MIN_PLAYED_AUDIO_IN_MILLIS_TO_STOP = 1000

class MediaAudioPlayer @Inject constructor(
    private val context: Context,
    private val mediaPlayer: MediaPlayer,
    private val appDispatchers: AppDispatchers
): AudioPlayer, MediaPlayer.OnPreparedListener {

    private var isMediaAudioPlayerPrepared = false

    private var _isAudioPlaying: Boolean = false

    override val isAudioPlaying: Boolean
        get() = _isAudioPlaying

    override fun init(fileUri: Uri) {
        if (!isMediaAudioPlayerPrepared) {
            mediaPlayer.apply {
                setDataSource(context, fileUri)
                setOnPreparedListener(this@MediaAudioPlayer)
                prepareAsync()
            }
        }
    }

    override fun onPrepared(mediaPlayer: MediaPlayer?) {
        isMediaAudioPlayerPrepared = true
    }

    override fun play() {
        if (isMediaAudioPlayerPrepared && !mediaPlayer.isPlaying) {
            mediaPlayer.start()
            _isAudioPlaying = true
        } else if (!isMediaAudioPlayerPrepared) {
            mediaPlayer.prepareAsync()
        }
    }

    override fun pause() {
        if (isMediaAudioPlayerPrepared && mediaPlayer.isPlaying) {
            mediaPlayer.pause()
            _isAudioPlaying = false
        }
    }

    override fun stop() {
        if (isMediaAudioPlayerPrepared && mediaPlayer.currentPosition >= MIN_PLAYED_AUDIO_IN_MILLIS_TO_STOP) {
            mediaPlayer.stop()
            isMediaAudioPlayerPrepared = false
            _isAudioPlaying = false
        }
    }

    override suspend fun release() {
        withContext(appDispatchers.ioDispatcher) {
            mediaPlayer.release()
        }
    }
}