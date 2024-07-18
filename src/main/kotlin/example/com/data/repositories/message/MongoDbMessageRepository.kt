package example.com.data.repositories.message

import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
import example.com.data.model.Message
import example.com.utils.exception.ExceptionHandler
import example.com.utils.execute

class MongoDbMessageRepository(
    private val mongoDatabase: MongoDatabase,
    private val exceptionHandler: ExceptionHandler
): MessageRepository {

    private val messagesCollectionName = System.getenv("MESSAGE_COLLECTION")

    private val messagesCollection = mongoDatabase.getCollection(messagesCollectionName, Message::class.java)

    override suspend fun getAllMessages(): List<Message> {
        return execute(
            executionBlock = {
                messagesCollection.find()
                    .sortedByDescending { message ->
                        message.timestamp
                    }
            },
            exceptionHandler
        ) ?: emptyList()
    }

    override suspend fun insertMessage(message: Message) {
        execute(
            executionBlock = {
                messagesCollection.insertOne(message)
            },
            exceptionHandler
        )
    }

    override suspend fun deleteMessage(id: String) {
        execute(
            executionBlock = {
                val queryBson = Filters.eq(Message::id.name, id)
                messagesCollection.deleteOne(queryBson)
            },
            exceptionHandler
        )
    }
}