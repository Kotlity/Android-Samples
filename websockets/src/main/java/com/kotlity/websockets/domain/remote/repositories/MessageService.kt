package com.kotlity.websockets.domain.remote.repositories

import com.kotlity.websockets.domain.models.Message
import com.kotlity.websockets.utils.responses.NetworkFailure
import com.kotlity.websockets.utils.responses.HandleStatus
import kotlinx.coroutines.flow.Flow

interface MessageService {

    fun getAllMessages(): Flow<HandleStatus<List<Message>, NetworkFailure>>

    suspend fun deleteMessage(id: String): HandleStatus<Unit, NetworkFailure>
}