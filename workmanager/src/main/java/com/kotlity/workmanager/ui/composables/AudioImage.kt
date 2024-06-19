package com.kotlity.workmanager.ui.composables

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Audiotrack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap

@Composable
fun AudioImage(
    modifier: Modifier = Modifier,
    image: Bitmap? = null
) {
    if (image == null) {
        Image(
            modifier = modifier,
            imageVector = Icons.Default.Audiotrack,
            contentDescription = null
        )
    } else {
        Image(
            modifier = modifier,
            bitmap = image.asImageBitmap(),
            contentDescription = null
        )
    }
}