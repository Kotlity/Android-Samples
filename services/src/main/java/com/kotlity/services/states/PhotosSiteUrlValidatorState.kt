package com.kotlity.services.states

import com.kotlity.services.helpers.UiText

sealed interface PhotosSiteUrlValidatorState {
    data object Success: PhotosSiteUrlValidatorState
    data class Error(val errorMessage: UiText): PhotosSiteUrlValidatorState
    data object Undefined: PhotosSiteUrlValidatorState
}