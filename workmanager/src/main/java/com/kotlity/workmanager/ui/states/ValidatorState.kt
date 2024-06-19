package com.kotlity.workmanager.ui.states

import com.kotlity.workmanager.utils.ErrorType

sealed interface ValidatorState {
    data object Success: ValidatorState
    data class Error(val errorType: ErrorType): ValidatorState
    data object Initial: ValidatorState
}