package example.com.plugins

import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.routing.*

fun Application.configureStaticResources() {
    routing {
        staticResources("/resources", "static")
    }
}