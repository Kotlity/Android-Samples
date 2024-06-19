package com.kotlity.workmanager.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.MainCoroutineDispatcher

data class AppDispatchers(
    val mainDispatcher: MainCoroutineDispatcher,
    val ioDispatcher: CoroutineDispatcher,
    val defaultDispatcher: CoroutineDispatcher,
    val unconfinedDispatcher: CoroutineDispatcher
)
