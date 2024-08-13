package com.kotlity.websockets.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatTopAppBar(
    connectionStatus: String?,
    username: String?
) {

    val titleStyleMedium = MaterialTheme.typography.titleMedium

    TopAppBar(title = {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            connectionStatus?.let {
                Text(
                    modifier = Modifier.weight(1f),
                    text = it,
                    style = titleStyleMedium
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            username?.let {
                Text(
                    modifier = Modifier.weight(1f),
                    text = it,
                    style = titleStyleMedium
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    },
        colors = TopAppBarDefaults.topAppBarColors().copy(
            containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}