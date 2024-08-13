package com.kotlity.websockets.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.sp
import com.kotlity.websockets.R
import com.kotlity.websockets.presentation.events.ConnectChatEvent
import com.kotlity.websockets.presentation.events.NavigationEvent
import com.kotlity.websockets.utils.asUiText
import com.kotlity.websockets.utils.destinations.ChatDestinations
import com.kotlity.websockets.utils.responses.HandleStatus
import com.kotlity.websockets.utils.responses.UsernameValidationFailure
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ConnectChatScreen(
    usernameState: String,
    usernameValidationStatus: HandleStatus<Unit, UsernameValidationFailure>,
    navigationFlow: Flow<NavigationEvent<String>>,
    onEvent: (ConnectChatEvent) -> Unit,
    onNavigateToChat: (ChatDestinations.Chat) -> Unit
) {
    
    val isError = usernameValidationStatus.isError()
    val isSuccess = usernameValidationStatus.isSuccess()

    LaunchedEffect(key1 = Unit) {
        navigationFlow.collectLatest { navigationEvent ->
            when(navigationEvent) {
                is NavigationEvent.OnNavigate -> {
                    val username = navigationEvent.data
                    username?.let {
                        onNavigateToChat(ChatDestinations.Chat(username = it))
                    }
                }
            }
        }
    }
    
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(0.7f),
            value = usernameState,
            onValueChange = {
                onEvent(ConnectChatEvent.OnUsernameUpdate(it))
            },
            label = {
                Text(
                    text = stringResource(id = R.string.connectChatTextFieldLabel),
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            },
            supportingText = {
                if (isError) {
                    Text(
                        text = (usernameValidationStatus as HandleStatus.Error).error.asUiText().asComposeString(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
            },
            isError = isError,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen._10dp)))
        OutlinedButton(
            onClick = {
                onEvent(ConnectChatEvent.OnNavigate<String>())
            },
            enabled = isSuccess
        ) {
            Text(
                text = stringResource(id = R.string.connectChatButtonTitle, usernameState),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}