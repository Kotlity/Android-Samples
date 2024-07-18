package example.com.data.repositories.room

import example.com.data.model.Message
import example.com.data.repositories.message.MessageRepository
import example.com.data.repositories.parser.Parser
import example.com.room.Member
import example.com.utils.exception.types.EncodeMessageException
import example.com.utils.exception.types.MemberAlreadyExistsException
import io.ktor.websocket.*
import java.util.concurrent.ConcurrentHashMap

class RoomController(
    private val messageRepository: MessageRepository,
    private val parser: Parser
): Controller {

    private val members = ConcurrentHashMap<String, Member>()

    private fun isMemberAlreadyJoined(username: String) = members.containsKey(username)

    override fun join(member: Member) {
        if (isMemberAlreadyJoined(member.username)) throw MemberAlreadyExistsException()
        members[member.username] = member
    }

    private fun encodeMessage(message: Message) = parser.encodeToString(message, Message::class)

    override suspend fun sendMessage(message: Message) {
        members.values.forEach { member ->
            messageRepository.insertMessage(message)

            encodeMessage(message)?.let { encodedMessage ->
                val textFrameType = Frame.Text(encodedMessage)
                member.webSocketSession.send(textFrameType)
            } ?: throw EncodeMessageException()
        }
    }

    override suspend fun getAllMessages(): List<Message> = messageRepository.getAllMessages()

    override suspend fun deleteMessage(id: String) {
        messageRepository.deleteMessage(id)
    }

    override suspend fun tryDisconnect(username: String) {
        members[username]?.webSocketSession?.close()
        if (isMemberAlreadyJoined(username)) members.remove(username)
    }
}