package example.com.plugins

import example.com.sessions.ChatSession
import io.ktor.server.application.*
import io.ktor.server.sessions.*
import io.ktor.util.*
import io.ktor.util.pipeline.*

private val COOKIE_NAME = System.getenv("COOKIE_NAME")

fun Application.configureSessions() {
    install(Sessions) {
        cookie<ChatSession>(COOKIE_NAME)
    }

    intercept(ApplicationCallPipeline.Plugins) {
        if (!isSessionAlreadyCreatedForParticularMember()) {
            val username = call.parameters["username"] ?: "Guest"
            val chatSession = ChatSession(
                username = username,
                sessionId = generateNonce()
            )
            call.sessions.set(chatSession)
        }
    }
}

private fun PipelineContext<Unit, ApplicationCall>.isSessionAlreadyCreatedForParticularMember() = call.sessions.get<ChatSession>() != null