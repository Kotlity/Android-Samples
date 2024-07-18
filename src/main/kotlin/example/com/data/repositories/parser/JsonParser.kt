package example.com.data.repositories.parser

import example.com.utils.exception.ExceptionHandler
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import kotlin.reflect.KClass

@OptIn(InternalSerializationApi::class)
class JsonParser(
    private val json: Json,
    private val exceptionHandler: ExceptionHandler
): Parser {

    override fun <T: Any> encodeToString(data: T, clazz: KClass<T>): String? {
        return try {
            val serializer = clazz.serializer()
            json.encodeToString(serializer, data)
        } catch (e: Exception) {
            exceptionHandler(e)
            null
        }
    }

    override fun <T: Any> decodeFromString(data: String, clazz: KClass<T>): T? {
        return try {
            val deserializer = clazz.serializer()
            json.decodeFromString(deserializer, data)
        } catch (e: Exception) {
            exceptionHandler(e)
            null
        }
    }
}