package example.com.utils.exception.types

private val ERROR_MESSAGE = System.getenv("MEMBER_ALREADY_EXISTS")

class MemberAlreadyExistsException: Exception(ERROR_MESSAGE)