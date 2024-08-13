package com.kotlity.websockets.utils.responses

enum class UsernameValidationFailure: Failure {
    IS_BLANK,
    CONTAINS_DIGITS,
    CONTAINS_PROHIBITED_CHARACTERS
}