package com.kotlity.services.validator

class IsTextBlankValidator {

    operator fun invoke(text: String): Boolean {
        return text.isBlank()
    }
}