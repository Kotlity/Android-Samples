package com.kotlity.websockets.presentation.composables

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ChatScaffold(
    modifier: Modifier = Modifier,
    topAppBar: @Composable () -> Unit,
    snackbarHost: @Composable () -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = { topAppBar() },
        snackbarHost = { snackbarHost() }
    ) { innerPadding ->
        content(innerPadding)
    }
}