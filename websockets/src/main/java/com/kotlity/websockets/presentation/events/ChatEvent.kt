package com.kotlity.websockets.presentation.events

sealed interface ChatEvent {
    data class OnMessageTextUpdate(val messageText: String): ChatEvent
    data object OnSendMessage: ChatEvent
}