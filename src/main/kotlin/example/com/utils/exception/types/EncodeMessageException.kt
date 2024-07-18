package example.com.utils.exception.types

private val ERROR_MESSAGE = System.getenv("ENCODE_MESSAGE")

class EncodeMessageException: Exception(ERROR_MESSAGE)