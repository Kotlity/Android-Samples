package com.kotlity.workmanager.di.module

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import com.kotlity.workmanager.repositories.media.AudioPlayer
import com.kotlity.workmanager.repositories.media.MediaAudioPlayer
import com.kotlity.workmanager.utils.AppDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MediaAudioPlayerModule {

    @Provides
    @Singleton
    fun provideMediaPlayer(): MediaPlayer {
        val audioAttributes = AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .build()
        return MediaPlayer().apply {
            setAudioAttributes(audioAttributes)
        }
    }

    @Provides
    @Singleton
    fun provideAudioPlayer(
        @ApplicationContext context: Context,
        mediaPlayer: MediaPlayer,
        appDispatchers: AppDispatchers
    ): AudioPlayer {
        return MediaAudioPlayer(
            context = context,
            mediaPlayer = mediaPlayer,
            appDispatchers = appDispatchers
        )
    }
}