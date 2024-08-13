package com.kotlity.websockets.presentation.events

import com.kotlity.websockets.utils.UiText

sealed interface ResponseEvent<out D, out E: UiText> {
    data class Success<D, E: UiText>(val data: D? = null): ResponseEvent<D, E>
    data class Error<D, E: UiText>(val errorMessage: E): ResponseEvent<D, E>
}