package com.kotlity.websockets.domain.remote.repositories

import com.kotlity.websockets.domain.models.Message
import com.kotlity.websockets.utils.responses.HandleStatus
import com.kotlity.websockets.utils.responses.SocketFailure
import com.kotlity.websockets.utils.responses.SocketSuccess
import kotlinx.coroutines.flow.Flow

interface SocketService {

    suspend fun openSocketSession(username: String): HandleStatus<SocketSuccess, SocketFailure>

    suspend fun sendMessage(text: String): HandleStatus<Unit, SocketFailure>

    fun retrieveMessages(): Flow<HandleStatus<Message, SocketFailure>>

    suspend fun closeSocketSession()
}