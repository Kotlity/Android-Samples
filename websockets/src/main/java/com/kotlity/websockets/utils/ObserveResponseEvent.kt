package com.kotlity.websockets.utils

import android.content.Context
import com.kotlity.websockets.presentation.events.ResponseEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

suspend inline fun <D, E: UiText> Flow<ResponseEvent<D, E>>.observeResponseEvent(
    context: Context,
    noinline onSuccess: ((D?) -> Unit)? = null,
    crossinline onError: suspend (String) -> Unit
) {
    collectLatest { responseEvent ->
        when(responseEvent) {
            is ResponseEvent.Error -> {
                val error = responseEvent.errorMessage.asString(context)
                onError(error)
            }
            is ResponseEvent.Success -> onSuccess?.let { it(responseEvent.data) }
        }
    }
}