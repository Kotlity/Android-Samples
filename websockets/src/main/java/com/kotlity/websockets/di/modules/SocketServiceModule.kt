package com.kotlity.websockets.di.modules

import com.kotlity.websockets.data.remote.repositories_impl.ChatSocketService
import com.kotlity.websockets.domain.remote.repositories.SocketService
import com.kotlity.websockets.utils.repositories.PropertiesReceiver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SocketServiceModule {

    @Provides
    @Singleton
    fun provideSocketService(
        ktorClient: HttpClient,
        propertiesReceiver: PropertiesReceiver
    ): SocketService {
        return ChatSocketService(
            ktorClient = ktorClient,
            propertiesReceiver = propertiesReceiver
        )
    }
}