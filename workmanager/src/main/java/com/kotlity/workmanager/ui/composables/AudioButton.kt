package com.kotlity.workmanager.ui.composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun AudioButton(
    modifier: Modifier = Modifier,
    icon: ImageVector = Icons.Default.PlayCircle,
    color: IconButtonColors = IconButtonDefaults.outlinedIconButtonColors(containerColor = Color.Green),
    onButtonClick: () -> Unit
) {
    OutlinedIconButton(
        modifier = modifier,
        onClick = onButtonClick,
        colors = color
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null
        )
    }
}