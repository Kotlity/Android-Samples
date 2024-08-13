package com.kotlity.websockets.data.remote.repositories_impl

import com.kotlity.websockets.data.remote.dtos.MessageDto
import com.kotlity.websockets.domain.models.Message
import com.kotlity.websockets.domain.remote.repositories.SocketService
import com.kotlity.websockets.mappers.toMessage
import com.kotlity.websockets.utils.repositories.PropertiesReceiver
import com.kotlity.websockets.utils.responses.HandleStatus
import com.kotlity.websockets.utils.responses.SocketFailure
import com.kotlity.websockets.utils.responses.SocketSuccess
import com.kotlity.websockets.utils.socketOneTimeEvent
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.WebSocketException
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.http.URLProtocol
import io.ktor.http.appendPathSegments
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.close
import io.ktor.websocket.readText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.isActive
import kotlinx.serialization.json.Json
import java.net.ConnectException
import java.util.concurrent.TimeoutException
import javax.inject.Inject

class ChatSocketService @Inject constructor(
    private val ktorClient: HttpClient,
    propertiesReceiver: PropertiesReceiver
): SocketService {

    private val hostString = propertiesReceiver.receiveProperty("HOST")
    private val portString = propertiesReceiver.receiveProperty("PORT")

    private var webSocketSession: WebSocketSession? = null

    override suspend fun openSocketSession(username: String): HandleStatus<SocketSuccess, SocketFailure> {
        return socketOneTimeEvent {
            if (webSocketSession == null) {
                webSocketSession = ktorClient.webSocketSession {
                    url {
                        protocol = URLProtocol.WS
                        host = hostString ?: return@url
                        port = portString?.toInt() ?: return@url
                        appendPathSegments("chat-web-socket")
                        parameters.append("username", username)
                    }
                }
                if (webSocketSession!!.isActive) HandleStatus.Success(data = SocketSuccess.CONNECTION_ESTABLISHED)
                else HandleStatus.Error(error = SocketFailure.CONNECTION_ESTABLISH_EXCEPTION)
            } else HandleStatus.Error(error = SocketFailure.CONNECTION_ALREADY_ESTABLISHED)
        }
    }

    override suspend fun sendMessage(text: String): HandleStatus<Unit, SocketFailure> {
        return socketOneTimeEvent {
            webSocketSession?.let {
                it.send(Frame.Text(text))
                HandleStatus.Success(data = Unit)
            } ?: HandleStatus.Error(error = SocketFailure.CONNECTION_ESTABLISH_EXCEPTION)
        }
    }

    override fun retrieveMessages(): Flow<HandleStatus<Message, SocketFailure>> {
        return try {
            webSocketSession?.let {
                it.incoming.receiveAsFlow()
                    .filter { frame -> frame is Frame.Text }
                    .map { textFrame ->
                        val jsonStringResponse = (textFrame as Frame.Text).readText()
                        val messageDto = Json.decodeFromString<MessageDto>(jsonStringResponse)
                        val message = messageDto.toMessage()
                        HandleStatus.Success(data = message)
                    }
            } ?: flowOf(HandleStatus.Error(error = SocketFailure.CONNECTION_ESTABLISH_EXCEPTION))
        } catch (e: TimeoutException) {
            e.printStackTrace()
            flowOf(HandleStatus.Error(error = SocketFailure.TIMEOUT_EXCEPTION))
        } catch (e: ConnectException) {
            e.printStackTrace()
            flowOf(HandleStatus.Error(error = SocketFailure.CONNECT_EXCEPTION))
        } catch (e: WebSocketException) {
            e.printStackTrace()
            flowOf(HandleStatus.Error(error = SocketFailure.WEBSOCKET_EXCEPTION))
        }
    }

    override suspend fun closeSocketSession() {
        webSocketSession?.close()
        webSocketSession = null
    }
}