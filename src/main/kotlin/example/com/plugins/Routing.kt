package example.com.plugins

import example.com.data.repositories.room.Controller
import example.com.routes.chatWebSocketRoute
import example.com.routes.getAllMessagesRoute
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val controller: Controller by inject()
    routing {
        chatWebSocketRoute(controller = controller)
        getAllMessagesRoute(controller = controller)
    }
}