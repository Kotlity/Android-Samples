package com.kotlity.network.view.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.kotlity.network.view.screens.FakeApiScreen
import com.kotlity.network.view.ui.theme.NetworkTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FakeApiActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NetworkTheme {
                FakeApiScreen()
            }
        }
    }
}