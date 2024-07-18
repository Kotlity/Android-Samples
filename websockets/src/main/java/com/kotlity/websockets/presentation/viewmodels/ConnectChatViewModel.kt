package com.kotlity.websockets.presentation.viewmodels

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlity.websockets.di.qualifiers.UsernameValidatorQualifier
import com.kotlity.websockets.domain.remote.repositories.Validator
import com.kotlity.websockets.presentation.events.ConnectChatEvent
import com.kotlity.websockets.presentation.events.NavigationEvent
import com.kotlity.websockets.utils.responses.HandleStatus
import com.kotlity.websockets.utils.responses.UsernameValidationFailure
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConnectChatViewModel @Inject constructor(
    @UsernameValidatorQualifier
    private val usernameValidator: Validator<Unit, UsernameValidationFailure>
): ViewModel() {

    var usernameState by mutableStateOf("")
        private set

    var usernameValidationStatus by derivedStateOf {
        mutableStateOf<HandleStatus<Unit, UsernameValidationFailure>>(HandleStatus.Undefined())
    }.value
        private set

    private val navigationChannel = Channel<NavigationEvent<String>>()
    val navigationFlow = navigationChannel.receiveAsFlow()

    fun onEvent(connectChatEvent: ConnectChatEvent) {
        when(connectChatEvent) {
            is ConnectChatEvent.OnUsernameUpdate -> onUsernameUpdate(username = connectChatEvent.username)
            is ConnectChatEvent.OnNavigate<*> -> onNavigate()
        }
    }

    private fun onUsernameUpdate(username: String) {
        usernameState = username
        usernameValidationStatusUpdate(username = username)
    }

    private fun usernameValidationStatusUpdate(username: String) {
        usernameValidationStatus = usernameValidator.validate(username)
    }

    private fun onNavigate() {
        viewModelScope.launch {
            navigationChannel.send(NavigationEvent.OnNavigate(data = usernameState))
        }
    }
}