package com.kotlity.websockets.domain.remote.repositories

import com.kotlity.websockets.utils.responses.Failure
import com.kotlity.websockets.utils.responses.HandleStatus

interface Validator<T, E: Failure> {

    fun validate(input: String): HandleStatus<T, E>
}