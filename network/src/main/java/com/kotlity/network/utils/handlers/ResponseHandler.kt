package com.kotlity.network.utils.handlers

import com.kotlity.network.utils.NetworkFailure
import com.kotlity.network.utils.Response
import com.kotlity.network.utils.UiText
import com.kotlity.network.utils.asUiText

inline fun <R, E: NetworkFailure> Response<R, E>.handleResponse(
    noinline onLoading: (() -> Unit)? = null,
    crossinline onSuccess: (R) -> Unit,
    crossinline onError: (UiText) -> Unit
) {
    when(this) {
        is Response.Loading -> onLoading?.invoke()
        is Response.Success -> {
            onSuccess(data)
        }
        is Response.Error -> {
            onError(errorMessage.asUiText())
        }
    }
}