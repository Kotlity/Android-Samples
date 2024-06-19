package com.kotlity.workmanager.utils

import androidx.work.Data
import androidx.work.ListenableWorker
import androidx.work.workDataOf

fun ListenableWorker.setResult(success: Boolean, retry: Boolean = false, vararg pairs: Pair<String, Any?> = emptyArray()): ListenableWorker.Result {
    val data = if (pairs.isNotEmpty()) workDataOf(*pairs) else Data.EMPTY

    return when {
        retry -> ListenableWorker.Result.retry()
        success -> ListenableWorker.Result.success(data)
        else -> ListenableWorker.Result.failure(data)
    }
}