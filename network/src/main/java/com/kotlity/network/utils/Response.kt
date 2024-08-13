package com.kotlity.network.utils

sealed interface Failure

enum class NetworkFailure: Failure {
    CONNECT_EXCEPTION,
    NO_ROUTE_TO_HOST_EXCEPTION,
    MALFORMED_URL_EXCEPTION,
    SSL_EXCEPTION,
    TIMEOUT_EXCEPTION,
    HTTP_EXCEPTION,
    IO_EXCEPTION,
    NO_DATA_RECEIVED
}

sealed interface Response<T, E: Failure> {
    data class Success<T, E: Failure>(val data: T): Response<T, E>
    data class Error<T, E: Failure>(val errorMessage: E): Response<T, E>
    class Loading<T, E: Failure>: Response<T, E>
}