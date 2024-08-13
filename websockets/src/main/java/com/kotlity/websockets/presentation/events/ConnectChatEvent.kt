package com.kotlity.websockets.presentation.events

sealed interface ConnectChatEvent {
    data class OnUsernameUpdate(val username: String): ConnectChatEvent
    data class OnNavigate<D>(val data: D? = null): ConnectChatEvent
}