package com.kotlity.services.validator

import com.kotlity.services.helpers.ValidatorState

interface TextValidator {

    fun validateText(text: String): ValidatorState
}