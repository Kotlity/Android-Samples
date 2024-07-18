package example.com.di.modules

import com.mongodb.ConnectionString
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import org.koin.dsl.module

private val MONGO_DB_CONNECTION = System.getenv("MONGO_DB_CONNECTION")
private val MONGO_DB_NAME = System.getenv("MONGO_DB_NAME")

val mongoDBModule = module {
    single<MongoClient> {
        val connectionString = ConnectionString(MONGO_DB_CONNECTION)
        MongoClients.create(connectionString)
    }
    single<MongoDatabase> {
        val mongoClient: MongoClient = get()
        mongoClient.getDatabase(MONGO_DB_NAME)
    }
}