package com.kotlity.network.utils

import com.kotlity.network.R

fun NetworkFailure.asUiText(): UiText {
    return when(this) {
        NetworkFailure.CONNECT_EXCEPTION -> UiText.StringResource(R.string.connectException)
        NetworkFailure.NO_ROUTE_TO_HOST_EXCEPTION -> UiText.StringResource(R.string.noRouteToHostException)
        NetworkFailure.MALFORMED_URL_EXCEPTION -> UiText.StringResource(R.string.malformedUrlException)
        NetworkFailure.SSL_EXCEPTION -> UiText.StringResource(R.string.sslException)
        NetworkFailure.TIMEOUT_EXCEPTION -> UiText.StringResource(R.string.timeoutException)
        NetworkFailure.HTTP_EXCEPTION -> UiText.StringResource(R.string.httpException)
        NetworkFailure.IO_EXCEPTION -> UiText.StringResource(R.string.ioException)
        NetworkFailure.NO_DATA_RECEIVED -> UiText.StringResource(R.string.noDataReceived)
    }
}

fun Response.Error<*, NetworkFailure>.asErrorUiText(): UiText {
    return errorMessage.asUiText()
}