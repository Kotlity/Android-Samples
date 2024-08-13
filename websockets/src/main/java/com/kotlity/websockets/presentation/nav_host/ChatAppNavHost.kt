package com.kotlity.websockets.presentation.nav_host

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.kotlity.websockets.presentation.screens.ChatScreen
import com.kotlity.websockets.presentation.screens.ConnectChatScreen
import com.kotlity.websockets.presentation.viewmodels.ChatViewModel
import com.kotlity.websockets.presentation.viewmodels.ConnectChatViewModel
import com.kotlity.websockets.utils.destinations.ChatDestinations

@Composable
fun ChatAppNavHost() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ChatDestinations.ConnectChat
    ) {
        composable<ChatDestinations.ConnectChat> {
            val connectChatViewModel: ConnectChatViewModel = hiltViewModel()

            val usernameState = connectChatViewModel.usernameState
            val usernameValidationStatus = connectChatViewModel.usernameValidationStatus
            val navigationFlow = connectChatViewModel.navigationFlow

            ConnectChatScreen(
                usernameState = usernameState,
                usernameValidationStatus = usernameValidationStatus,
                navigationFlow = navigationFlow,
                onEvent = connectChatViewModel::onEvent,
                onNavigateToChat = { chatDestination ->
                    navController.navigate(chatDestination) {
                        popUpTo(ChatDestinations.ConnectChat) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable<ChatDestinations.Chat> { navBackStackEntry ->
            val chatRoute = navBackStackEntry.toRoute<ChatDestinations.Chat>()
            val chatViewModel: ChatViewModel = hiltViewModel()

            val chatState = chatViewModel.chatState
            val messageText = chatViewModel.messageText
            val isMessageTextBlank = chatViewModel.isMessageTextBlank
            val getAllMessagesResultFlow = chatViewModel.getAllMessagesResultFlow
            val retrieveMessagesResultFlow = chatViewModel.retrieveMessagesResultFlow
            val sendMessageResultFlow = chatViewModel.sendMessageResultFlow

            ChatScreen(
                chatState = chatState,
                username = chatRoute.username,
                messageText = messageText,
                isMessageTextBlank = isMessageTextBlank,
                getAllMessagesResultFlow = getAllMessagesResultFlow,
                retrieveMessagesResultFlow = retrieveMessagesResultFlow,
                sendMessageResultFlow = sendMessageResultFlow,
                onEvent = chatViewModel::onEvent
            )
        }
    }
}