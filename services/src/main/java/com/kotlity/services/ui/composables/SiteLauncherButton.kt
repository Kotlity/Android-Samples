package com.kotlity.services.ui.composables

import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.kotlity.services.R

@Composable
fun SiteLauncherButton(
    modifier: Modifier = Modifier,
    isEnabled: Boolean = false,
    onButtonClick: () -> Unit
) {
    OutlinedButton(
        modifier = modifier,
        enabled = isEnabled,
        onClick = onButtonClick
    ) {
        Text(text = stringResource(id = R.string.siteLauncherButtonText))
    }
}