package example.com.routes

import example.com.data.repositories.room.Controller
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

private val MESSAGES_ROUTE = System.getenv("MESSAGES_ROUTE")

fun Route.getAllMessagesRoute(controller: Controller) {
    get(path = MESSAGES_ROUTE) {
        val allMessages = controller.getAllMessages()
        call.respond(HttpStatusCode.OK, allMessages)
    }
}