package com.kotlity.workmanager.repositories.validator

class IsTextBlankValidator {

    operator fun invoke(text: String): Boolean {
        return text.isBlank()
    }
}