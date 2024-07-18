package example.com.utils

import example.com.utils.exception.ExceptionHandler

suspend inline fun <reified T> execute(
    executionBlock: suspend () -> T,
    exceptionHandler: ExceptionHandler
): T? {
    return try {
        executionBlock()
    } catch (e: Exception) {
        exceptionHandler(e)
        null
    }
}