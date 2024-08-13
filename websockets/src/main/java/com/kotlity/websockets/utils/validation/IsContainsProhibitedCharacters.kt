package com.kotlity.websockets.utils.validation

import javax.inject.Inject

class IsContainsProhibitedCharacters @Inject constructor() {

    operator fun invoke(input: String) = input.contains(Regex("[!@\\\$%^&*(),?\\\":{}|<>]"))
}