package com.kotlity.websockets.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.kotlity.websockets.R
import com.kotlity.websockets.presentation.composables.ChatLazyColumn
import com.kotlity.websockets.presentation.composables.ChatScaffold
import com.kotlity.websockets.presentation.composables.ChatSnackbarHost
import com.kotlity.websockets.presentation.composables.ChatTopAppBar
import com.kotlity.websockets.presentation.events.ChatEvent
import com.kotlity.websockets.presentation.events.ResponseEvent
import com.kotlity.websockets.presentation.states.ChatState
import com.kotlity.websockets.utils.UiText
import com.kotlity.websockets.utils.CustomSnackbarVisuals
import com.kotlity.websockets.utils.observeResponseEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Composable
fun ChatScreen(
    chatState: ChatState,
    username: String,
    messageText: String,
    isMessageTextBlank: Boolean,
    getAllMessagesResultFlow: Flow<ResponseEvent<Unit, UiText>>,
    retrieveMessagesResultFlow: Flow<ResponseEvent<Unit, UiText>>,
    sendMessageResultFlow: Flow<ResponseEvent<Unit, UiText>>,
    onEvent: (ChatEvent) -> Unit
) {

    val context = LocalContext.current
    val snackbarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(key1 = Unit) {
        launch {
            getAllMessagesResultFlow.observeResponseEvent(context) {
                snackbarHostState.showSnackbar(CustomSnackbarVisuals(message = it))
            }
        }
        launch {
            retrieveMessagesResultFlow.observeResponseEvent(context) {
                snackbarHostState.showSnackbar(CustomSnackbarVisuals(message = it))
            }
        }
        launch {
            sendMessageResultFlow.observeResponseEvent(context) {
                snackbarHostState.showSnackbar(CustomSnackbarVisuals(message = it))
            }
        }
    }

    ChatScaffold(
        modifier = Modifier.fillMaxSize(),
        topAppBar = {
            ChatTopAppBar(
                connectionStatus = chatState.connectionStatus?.asComposeString(),
                username = username
            )
        },
        snackbarHost = {
            ChatSnackbarHost(snackbarHostState = snackbarHostState)
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(dimensionResource(id = R.dimen._10dp))
            ) {
                ChatLazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    messages = chatState.messages,
                    username = username
                )
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = messageText,
                    onValueChange = {
                        onEvent(ChatEvent.OnMessageTextUpdate(it))
                    },
                    placeholder = {
                        Text(text = stringResource(id = R.string.messageTextFieldPlaceholder))
                    },
                    trailingIcon = {
                        if (!isMessageTextBlank) {
                            IconButton(
                                onClick = { onEvent(ChatEvent.OnSendMessage) }
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.Send,
                                    contentDescription = null
                                )
                            }
                        }
                    }
                )
            }
        }
    )
}