package example.com.routes

import example.com.data.model.Message
import example.com.data.repositories.room.Controller
import example.com.room.Member
import example.com.sessions.ChatSession
import example.com.utils.exception.types.MemberAlreadyExistsException
import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.consumeEach

private val WEB_SOCKET_ROUTE = System.getenv("WEB_SOCKET_ROUTE")

fun Route.chatWebSocketRoute(controller: Controller) {
    webSocket(path = WEB_SOCKET_ROUTE) {
        val currentSession = call.sessions.get<ChatSession>()
        if (currentSession == null) {
            val closeReasonMessage = System.getenv("CLOSE_REASON")
            close(reason = CloseReason(CloseReason.Codes.VIOLATED_POLICY, closeReasonMessage))
            return@webSocket
        }
        try {
            val joinedMember = Member(
                username = currentSession.username,
                sessionId = currentSession.sessionId,
                webSocketSession = this
            )
            controller.join(member = joinedMember)

            receiveDataFromWebSocketServerSession { frame ->
                val message = Message(
                    text = frame.readText(),
                    username = currentSession.username,
                    timestamp = System.currentTimeMillis()
                )
                controller.sendMessage(message)
            }
        } catch (e: MemberAlreadyExistsException) {
            call.respond(HttpStatusCode.Conflict)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        finally {
            controller.tryDisconnect(username = currentSession.username)
        }
    }
}

private suspend inline fun DefaultWebSocketServerSession.receiveDataFromWebSocketServerSession(crossinline onFrameConsumed: suspend (Frame.Text) -> Unit) {
    incoming.consumeEach { frame ->
        if (frame is Frame.Text) {
            onFrameConsumed(frame)
        }
    }
}