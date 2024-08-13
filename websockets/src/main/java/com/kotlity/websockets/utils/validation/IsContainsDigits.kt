package com.kotlity.websockets.utils.validation

import javax.inject.Inject

class IsContainsDigits @Inject constructor() {

    operator fun invoke(input: String) = input.any { it.isDigit() }
}