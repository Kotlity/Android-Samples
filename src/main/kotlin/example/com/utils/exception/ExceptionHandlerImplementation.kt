package example.com.utils.exception

class ExceptionHandlerImplementation: ExceptionHandler {

    override operator fun invoke(e: Exception) {
        println(e.message)
    }
}