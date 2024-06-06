package com.kotlity.services.ui.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage

@Composable
fun PhotoLoader(
    modifier: Modifier = Modifier,
    content: Any? = null
) {
    AsyncImage(
        modifier = modifier,
        model = content,
        contentDescription = null
    )
}