package com.kotlity.workmanager.repositories.validator

import com.kotlity.workmanager.ui.states.ValidatorState

interface TextValidator {

    fun validateText(text: String): ValidatorState
}