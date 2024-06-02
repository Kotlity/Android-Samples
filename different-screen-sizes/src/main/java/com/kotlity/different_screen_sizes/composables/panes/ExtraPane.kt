package com.kotlity.different_screen_sizes.composables.panes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.kotlity.different_screen_sizes.helpers.dataCuster

@Composable
fun ExtraPane(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.tertiaryContainer,
    contentAlignment: Alignment = Alignment.Center,
    detailData: Any? = null
) {

    val contentTextColor = MaterialTheme.colorScheme.onTertiaryContainer

    Box(
        modifier = modifier
            .background(color),
        contentAlignment = contentAlignment
    ) {
        Text(
            text = detailData.dataCuster("Default option"),
            color = contentTextColor
        )
    }
}