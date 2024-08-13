package com.kotlity.websockets.utils.responses

enum class NetworkFailure: Failure {
    CONNECT_EXCEPTION,
    NO_ROUTE_TO_HOST_EXCEPTION,
    MALFORMED_URL_EXCEPTION,
    SSL_EXCEPTION,
    TIMEOUT_EXCEPTION,
    NO_DATA_RECEIVED
}