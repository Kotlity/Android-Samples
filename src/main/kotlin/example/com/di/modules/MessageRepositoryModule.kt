package example.com.di.modules

import example.com.data.repositories.message.MessageRepository
import example.com.data.repositories.message.MongoDbMessageRepository
import org.koin.dsl.module

val messageRepositoryModule = module {
    single<MessageRepository> { MongoDbMessageRepository(mongoDatabase = get(), exceptionHandler = get()) }
}