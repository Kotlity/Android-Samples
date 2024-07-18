package example.com.di.modules

import example.com.data.repositories.room.Controller
import example.com.data.repositories.room.RoomController
import org.koin.dsl.module

val controllerModule = module {
    single<Controller> { RoomController(messageRepository = get(), parser = get()) }
}