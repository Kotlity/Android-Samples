package com.kotlity.websockets.utils

import com.kotlity.websockets.utils.responses.Failure
import com.kotlity.websockets.utils.responses.HandleStatus

inline fun <T, reified E: Failure> handleException(e: Exception, failure: E): HandleStatus<T, E> {
    e.printStackTrace()
    return HandleStatus.Error(error = failure)
}