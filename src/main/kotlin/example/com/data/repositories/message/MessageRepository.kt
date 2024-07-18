package example.com.data.repositories.message

import example.com.data.model.Message

interface MessageRepository {

    suspend fun getAllMessages(): List<Message>

    suspend fun insertMessage(message: Message)

    suspend fun deleteMessage(id: String)
}