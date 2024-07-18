package com.kotlity.websockets.presentation.viewmodels

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlity.websockets.domain.remote.repositories.MessageService
import com.kotlity.websockets.domain.remote.repositories.SocketService
import com.kotlity.websockets.presentation.events.ChatEvent
import com.kotlity.websockets.presentation.events.ResponseEvent
import com.kotlity.websockets.presentation.states.ChatState
import com.kotlity.websockets.utils.UiText
import com.kotlity.websockets.utils.asUiText
import com.kotlity.websockets.utils.responses.HandleStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val messageService: MessageService,
    private val socketService: SocketService,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    var chatState by mutableStateOf(ChatState())
        private set

    var messageText by mutableStateOf("")
        private set

    var isMessageTextBlank by derivedStateOf {
        mutableStateOf(true)
    }.value
        private set

    private val getAllMessagesResultChannel = Channel<ResponseEvent<Unit, UiText>>()
    val getAllMessagesResultFlow = getAllMessagesResultChannel.receiveAsFlow()

    private val retrieveMessagesResultChannel = Channel<ResponseEvent<Unit, UiText>>()
    val retrieveMessagesResultFlow = retrieveMessagesResultChannel.receiveAsFlow()

    private val sendMessageResultChannel = Channel<ResponseEvent<Unit, UiText>>()
    val sendMessageResultFlow = sendMessageResultChannel.receiveAsFlow()

    init {
        getAllMessages()
        connectChat()
    }

    fun onEvent(chatEvent: ChatEvent) {
        when(chatEvent) {
            is ChatEvent.OnMessageTextUpdate -> onMessageTextUpdate(messageText = chatEvent.messageText)
            ChatEvent.OnSendMessage -> onSendMessage()
        }
    }

    private fun getAllMessages() {
        messageService.getAllMessages().onEach { result ->
            when(result) {
                is HandleStatus.Error -> {
                    chatState = chatState.copy(messages = emptyList(), isLoading = false)
                    getAllMessagesResultChannel.send(ResponseEvent.Error(errorMessage = result.error.asUiText()))
                }
                is HandleStatus.Success -> chatState = chatState.copy(messages = result.data, isLoading = false)
                is HandleStatus.Undefined -> chatState = chatState.copy(messages = emptyList(), isLoading = true)
            }
        }
            .launchIn(viewModelScope)
    }

    private fun onMessageTextUpdate(messageText: String) {
        this.messageText = messageText
        isMessageTextBlank = this.messageText.isBlank()
    }

    private fun connectChat() {
        viewModelScope.launch {
            val username = savedStateHandle.get<String>("username")
            username?.let {
                when(val result = socketService.openSocketSession(username = it)) {
                    is HandleStatus.Error -> chatState = chatState.copy(connectionStatus = result.error.asUiText())
                    is HandleStatus.Success -> {
                        chatState = chatState.copy(connectionStatus = result.data.asUiText())
                        retrieveMessages()
                    }
                    else -> return@launch
                }
            }
        }
    }

    private fun retrieveMessages() {
        socketService.retrieveMessages()
            .onEach {
                when(it) {
                    is HandleStatus.Error -> retrieveMessagesResultChannel.send(ResponseEvent.Error(errorMessage = it.error.asUiText()))
                    is HandleStatus.Success -> {
                        val message = it.data
                        val editableMessagesChatList = chatState.messages.toMutableList()
                        editableMessagesChatList.add(0, message)
                        chatState = chatState.copy(messages = editableMessagesChatList.toList())
                    }
                    else -> return@onEach
                }
            }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    private fun onSendMessage() {
        viewModelScope.launch {
            val result = socketService.sendMessage(text = messageText)
            if (result is HandleStatus.Error) sendMessageResultChannel.send(ResponseEvent.Error(errorMessage = result.error.asUiText()))
        }
    }

    private fun disconnectFromChat() {
        viewModelScope.launch {
            socketService.closeSocketSession()
        }
    }

    override fun onCleared() {
        super.onCleared()
        disconnectFromChat()
    }
}