package com.kotlity.allpractice

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MainApplication: Application()
//    Configuration.Provider
{

//    @Inject
//    lateinit var hiltWorkerFactory: HiltWorkerFactory
//
//    override val workManagerConfiguration: Configuration
//        get() = Configuration.Builder()
//            .setWorkerFactory(hiltWorkerFactory)
//            .build()
}