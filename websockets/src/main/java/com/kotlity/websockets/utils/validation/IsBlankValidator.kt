package com.kotlity.websockets.utils.validation

import javax.inject.Inject

class IsBlankValidator @Inject constructor() {

    operator fun invoke(input: String) = input.isBlank()
}