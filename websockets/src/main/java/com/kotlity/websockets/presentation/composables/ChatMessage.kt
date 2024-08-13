package com.kotlity.websockets.presentation.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.kotlity.websockets.domain.models.Message

@Composable
fun ChatMessage(
    modifier: Modifier = Modifier,
    message: Message,
    isOwnMessage: Boolean
) {
    Box(
        modifier = modifier,
        contentAlignment = if (isOwnMessage) Alignment.CenterEnd else Alignment.CenterStart
    ) {
        ChatMessageBubble(message = message, isOwnMessage = isOwnMessage)
    }
}