package com.kotlity.websockets.di.modules

import com.kotlity.websockets.data.remote.repositories_impl.KtorMessageService
import com.kotlity.websockets.domain.remote.repositories.MessageService
import com.kotlity.websockets.utils.repositories.PropertiesReceiver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MessageServiceModule {

    @Provides
    @Singleton
    fun provideMessageService(
        ktorClient: HttpClient,
        propertiesReceiver: PropertiesReceiver
    ): MessageService {
        return KtorMessageService(
            ktorClient = ktorClient,
            propertiesReceiver = propertiesReceiver
        )
    }
}