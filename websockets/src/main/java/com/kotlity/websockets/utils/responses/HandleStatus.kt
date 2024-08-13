package com.kotlity.websockets.utils.responses

sealed interface HandleStatus<out D, out E: Failure> {
    data class Success<out D, out E: Failure>(val data: D): HandleStatus<D, E>
    data class Error<out D, out E: Failure>(val error: E): HandleStatus<D, E>
    class Undefined<out D, out E: Failure>: HandleStatus<D, E>

    fun isSuccess() = this is Success
    fun isError() = this is Error
    fun isUndefined() = this is Undefined
}