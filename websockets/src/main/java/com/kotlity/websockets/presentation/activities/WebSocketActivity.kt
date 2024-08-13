package com.kotlity.websockets.presentation.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.kotlity.websockets.presentation.nav_host.ChatAppNavHost
import com.kotlity.websockets.ui.theme.WebSocketsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WebSocketActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WebSocketsTheme {
                ChatAppNavHost()
            }
        }
    }
}