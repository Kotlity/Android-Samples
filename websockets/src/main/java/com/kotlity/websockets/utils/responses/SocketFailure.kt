package com.kotlity.websockets.utils.responses

enum class SocketFailure: Failure {
    CONNECTION_ALREADY_ESTABLISHED,
    CONNECTION_ESTABLISH_EXCEPTION,
    TIMEOUT_EXCEPTION,
    CONNECT_EXCEPTION,
    WEBSOCKET_EXCEPTION
}