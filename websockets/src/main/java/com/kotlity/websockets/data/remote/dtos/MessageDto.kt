package com.kotlity.websockets.data.remote.dtos

import kotlinx.serialization.Serializable

@Serializable
data class MessageDto(
    val _id: String,
    val username: String,
    val text: String,
    val images: List<ByteArray> = emptyList(),
    val timestamp: Long
)