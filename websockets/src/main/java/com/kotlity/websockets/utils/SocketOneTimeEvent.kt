package com.kotlity.websockets.utils

import com.kotlity.websockets.utils.responses.Failure
import com.kotlity.websockets.utils.responses.HandleStatus
import com.kotlity.websockets.utils.responses.SocketFailure
import io.ktor.client.plugins.websocket.WebSocketException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.ConnectException
import java.util.concurrent.TimeoutException
import kotlin.coroutines.CoroutineContext

suspend inline fun <reified T, reified E: Failure> socketOneTimeEvent(
    coroutineContext: CoroutineContext = Dispatchers.IO,
    crossinline block: suspend () -> HandleStatus<T, E>
): HandleStatus<T, E> {
    return withContext(coroutineContext) {
        try {
            block()
        } catch (e: TimeoutException) {
            handleException(e, SocketFailure.TIMEOUT_EXCEPTION as E)
        } catch (e: ConnectException) {
            handleException(e, SocketFailure.CONNECT_EXCEPTION as E)
        } catch (e: WebSocketException) {
            handleException(e, SocketFailure.WEBSOCKET_EXCEPTION as E)
        }
    }
}