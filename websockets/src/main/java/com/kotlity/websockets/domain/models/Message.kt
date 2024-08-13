package com.kotlity.websockets.domain.models

import android.graphics.Bitmap

data class Message(
    val id: String,
    val username: String,
    val text: String,
    val images: List<Bitmap> = emptyList(),
    val formattedTime: String
)
