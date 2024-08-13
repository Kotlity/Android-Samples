package com.kotlity.websockets.di.modules

import android.content.Context
import com.kotlity.websockets.utils.repositories.ConfigPropertiesReceiver
import com.kotlity.websockets.utils.repositories.PropertiesReceiver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PropertiesReceiverModule {

    @Provides
    @Singleton
    fun providePropertiesReceiver(@ApplicationContext appContext: Context): PropertiesReceiver {
        return ConfigPropertiesReceiver(appContext = appContext)
    }
}