package example.com.room

import io.ktor.websocket.*

data class Member(
    val username: String,
    val userAvatar: ByteArray? = null,
    val sessionId: String,
    val webSocketSession: WebSocketSession
)
