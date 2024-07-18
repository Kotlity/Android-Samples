package com.kotlity.websockets.utils.destinations

import kotlinx.serialization.Serializable

@Serializable
sealed interface ChatDestinations {
    @Serializable
    data object ConnectChat: ChatDestinations
    @Serializable
    data class Chat(val username: String): ChatDestinations
}