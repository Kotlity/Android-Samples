package com.kotlity.websockets.mappers

import com.kotlity.websockets.data.remote.dtos.MessageDto
import com.kotlity.websockets.domain.models.Message
import com.kotlity.websockets.utils.Constants.DEFAULT_TIME_PATTERN
import com.kotlity.websockets.utils.formatTime
import com.kotlity.websockets.utils.toBitmaps

fun MessageDto.toMessage(): Message {
    return Message(
        id = _id,
        username = username,
        text = text,
        images = images.toBitmaps(),
        formattedTime = timestamp.formatTime(DEFAULT_TIME_PATTERN)
    )
}