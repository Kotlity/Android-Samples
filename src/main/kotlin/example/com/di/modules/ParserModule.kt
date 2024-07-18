package example.com.di.modules

import example.com.data.repositories.parser.JsonParser
import example.com.data.repositories.parser.Parser
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val parserModule = module {
    single<Parser> { JsonParser(json = Json, exceptionHandler = get()) }
}