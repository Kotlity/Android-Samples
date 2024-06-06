package com.kotlity.services.helpers

sealed interface ValidatorState {
    data object Success: ValidatorState
    data class Error(val errorType: ErrorType): ValidatorState
    data object Undefined: ValidatorState
}