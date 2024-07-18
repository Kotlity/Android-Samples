package example.com.data.repositories.room

import example.com.data.model.Message
import example.com.room.Member

interface Controller {

    fun join(member: Member)

    suspend fun sendMessage(message: Message)

    suspend fun getAllMessages(): List<Message>

    suspend fun deleteMessage(id: String)

    suspend fun tryDisconnect(username: String)
}