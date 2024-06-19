package com.kotlity.workmanager.di.module

import android.app.DownloadManager
import android.content.Context
import android.media.MediaMetadataRetriever
import androidx.work.WorkManager
import com.kotlity.workmanager.repositories.file.AndroidFileDownloader
import com.kotlity.workmanager.repositories.file.AudioMetadataRetriever
import com.kotlity.workmanager.repositories.file.FileDownloader
import com.kotlity.workmanager.repositories.file.MetadataRetriever
import com.kotlity.workmanager.utils.AppDispatchers
import com.kotlity.workmanager.repositories.validator.IsCorrectSiteUrlFormatValidator
import com.kotlity.workmanager.repositories.validator.IsTextBlankValidator
import com.kotlity.workmanager.repositories.validator.SiteUrlValidator
import com.kotlity.workmanager.repositories.validator.TextValidator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DownloadMusicModule {

    @Provides
    @Singleton
    fun provideIsTextBlankValidator(): IsTextBlankValidator {
        return IsTextBlankValidator()
    }

    @Provides
    @Singleton
    fun provideIsCorrectSiteUrlFormatValidator(): IsCorrectSiteUrlFormatValidator {
        return IsCorrectSiteUrlFormatValidator()
    }

    @Provides
    @Singleton
    fun provideTextValidator(
        isTextBlankValidator: IsTextBlankValidator,
        isCorrectSiteUrlFormatValidator: IsCorrectSiteUrlFormatValidator
    ): TextValidator {
        return SiteUrlValidator(
            isTextBlankValidator = isTextBlankValidator,
            isCorrectSiteUrlFormatValidator = isCorrectSiteUrlFormatValidator
        )
    }

    @Provides
    @Singleton
    fun provideWorkManager(@ApplicationContext context: Context): WorkManager {
        return WorkManager.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideDownloadManager(@ApplicationContext context: Context): DownloadManager {
        return context.getSystemService(DownloadManager::class.java)
    }

    @Provides
    @Singleton
    fun provideFileDownloader(
        @ApplicationContext context: Context,
        downloadManager: DownloadManager
    ): FileDownloader {
        return AndroidFileDownloader(
            context = context,
            downloadManager = downloadManager
        )
    }

    @Provides
    @Singleton
    fun provideAppDispatchers(): AppDispatchers {
        return AppDispatchers(
            mainDispatcher = Dispatchers.Main,
            ioDispatcher = Dispatchers.IO,
            defaultDispatcher = Dispatchers.Default,
            unconfinedDispatcher = Dispatchers.Unconfined
        )
    }

    @Provides
    @Singleton
    fun provideMediaMetadataRetriever(): MediaMetadataRetriever {
        return MediaMetadataRetriever()
    }

    @Provides
    @Singleton
    fun provideMetadataRetriever(
        @ApplicationContext context: Context,
        mediaMetadataRetriever: MediaMetadataRetriever
    ): MetadataRetriever {
        return AudioMetadataRetriever(
            context = context,
            mediaMetadataRetriever = mediaMetadataRetriever
        )
    }
}