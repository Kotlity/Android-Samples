package com.kotlity.websockets.presentation.states

import com.kotlity.websockets.domain.models.Message
import com.kotlity.websockets.utils.UiText

data class ChatState(
    val messages: List<Message> = emptyList(),
    val isLoading: Boolean = false,
    val connectionStatus: UiText? = null
)