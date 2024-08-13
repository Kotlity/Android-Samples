package com.kotlity.websockets.presentation.composables

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.kotlity.websockets.R
import com.kotlity.websockets.domain.models.Message

@Composable
fun ChatLazyColumn(
    modifier: Modifier = Modifier,
    reverseLayout: Boolean = true,
    messages: List<Message>,
    username: String
) {
    LazyColumn(
        modifier = modifier,
        reverseLayout = reverseLayout
    ) {
        item {
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen._32dp)))
        }
        items(
            items = messages,
            key = { message -> message.id }
        ) { message ->
            val isOwnMessage = message.username == username
            ChatMessage(
                modifier = Modifier.fillMaxWidth(),
                message = message,
                isOwnMessage = isOwnMessage
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen._32dp)))
        }
    }
}