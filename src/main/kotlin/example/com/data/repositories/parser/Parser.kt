package example.com.data.repositories.parser

import kotlin.reflect.KClass

interface Parser {

    fun <T : Any> encodeToString(data: T, clazz: KClass<T>): String?

    fun <T: Any> decodeFromString(data: String, clazz: KClass<T>): T?
}