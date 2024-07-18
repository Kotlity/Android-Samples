package example.com.utils.exception

interface ExceptionHandler {

    operator fun invoke(e: Exception)
}