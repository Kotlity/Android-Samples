package example.com.plugins

import example.com.di.modules.*
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureKoin() {
    install(Koin) {
        slf4jLogger()
        modules(
            mongoDBModule,
            exceptionHandlerModule,
            messageRepositoryModule,
            parserModule,
            controllerModule
        )
    }
}