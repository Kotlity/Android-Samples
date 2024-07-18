package com.kotlity.websockets.utils

import com.kotlity.websockets.R
import com.kotlity.websockets.utils.responses.NetworkFailure
import com.kotlity.websockets.utils.responses.SocketFailure
import com.kotlity.websockets.utils.responses.SocketSuccess
import com.kotlity.websockets.utils.responses.UsernameValidationFailure

fun NetworkFailure.asUiText(): UiText {
    return when(this) {
        NetworkFailure.CONNECT_EXCEPTION -> UiText.StringResource(R.string.connectException)
        NetworkFailure.NO_ROUTE_TO_HOST_EXCEPTION -> UiText.StringResource(R.string.noRouteToHostException)
        NetworkFailure.MALFORMED_URL_EXCEPTION -> UiText.StringResource(R.string.malformedUrlException)
        NetworkFailure.SSL_EXCEPTION -> UiText.StringResource(R.string.SSLlException)
        NetworkFailure.TIMEOUT_EXCEPTION -> UiText.StringResource(R.string.timeoutException)
        NetworkFailure.NO_DATA_RECEIVED -> UiText.StringResource(R.string.noDataReceived)
    }
}

fun SocketFailure.asUiText(): UiText {
    return when(this) {
        SocketFailure.CONNECTION_ALREADY_ESTABLISHED -> UiText.StringResource(R.string.connectionAlreadyEstablished)
        SocketFailure.CONNECTION_ESTABLISH_EXCEPTION -> UiText.StringResource(R.string.connectionEstablishException)
        SocketFailure.TIMEOUT_EXCEPTION -> UiText.StringResource(R.string.timeoutException)
        SocketFailure.CONNECT_EXCEPTION -> UiText.StringResource(R.string.connectException)
        SocketFailure.WEBSOCKET_EXCEPTION -> UiText.StringResource(R.string.websocketException)
    }
}

fun SocketSuccess.asUiText(): UiText {
    return when(this) {
        SocketSuccess.CONNECTION_ESTABLISHED -> UiText.StringResource(R.string.connectionAlreadyEstablished)
    }
}

fun UsernameValidationFailure.asUiText(): UiText {
    return when(this) {
        UsernameValidationFailure.IS_BLANK -> UiText.StringResource(R.string.usernameIsBlank)
        UsernameValidationFailure.CONTAINS_DIGITS -> UiText.StringResource(R.string.usernameContainsDigits)
        UsernameValidationFailure.CONTAINS_PROHIBITED_CHARACTERS -> UiText.StringResource(R.string.usernameContainsProhibitedCharacters)
    }
}