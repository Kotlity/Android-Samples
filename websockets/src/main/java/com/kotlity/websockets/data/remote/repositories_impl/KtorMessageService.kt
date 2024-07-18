package com.kotlity.websockets.data.remote.repositories_impl

import com.kotlity.websockets.data.remote.dtos.MessageDto
import com.kotlity.websockets.domain.models.Message
import com.kotlity.websockets.domain.remote.repositories.MessageService
import com.kotlity.websockets.mappers.toMessage
import com.kotlity.websockets.utils.executeKtorCall
import com.kotlity.websockets.utils.executeKtorObservableCall
import com.kotlity.websockets.utils.repositories.PropertiesReceiver
import com.kotlity.websockets.utils.responses.NetworkFailure
import com.kotlity.websockets.utils.responses.HandleStatus
import io.ktor.client.HttpClient
import io.ktor.http.HttpMethod
import io.ktor.http.URLProtocol
import io.ktor.http.appendPathSegments
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class KtorMessageService @Inject constructor(
    private val ktorClient: HttpClient,
    propertiesReceiver: PropertiesReceiver
): MessageService {

    private val hostString = propertiesReceiver.receiveProperty("HOST")
    private val portString = propertiesReceiver.receiveProperty("PORT")

    override fun getAllMessages(): Flow<HandleStatus<List<Message>, NetworkFailure>> {
        return ktorClient.executeKtorObservableCall<List<MessageDto>, List<Message>>(
            httpRequestBuilder = {
                method = HttpMethod.Get
                url {
                    protocol = URLProtocol.HTTP
                    host = hostString ?: return@url
                    port = portString?.toInt() ?: return@url
                    appendPathSegments("messages")
                }
            },
            mapper = { messageDtos ->
                messageDtos.map { it.toMessage() }
            }
        )
    }

    override suspend fun deleteMessage(id: String): HandleStatus<Unit, NetworkFailure> {
        return ktorClient.executeKtorCall<Unit, Unit>(
            httpRequestBuilder = {
                method = HttpMethod.Delete
                url {
                    protocol = URLProtocol.HTTP
                    host = hostString ?: return@url
                    port = portString?.toInt() ?: return@url
                    appendPathSegments("messages", id)
                }
            }
        )
    }
}