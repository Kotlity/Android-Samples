package com.kotlity.websockets.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kotlity.websockets.R
import com.kotlity.websockets.domain.models.Message

@Composable
fun ChatMessageBubble(
    message: Message,
    isOwnMessage: Boolean
) {
    val messageColor = if (isOwnMessage) Color.Green else Color.Gray

    Column(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .drawBehind {
                val messageTrianglePath = messageTrianglePath(isOwnMessage)
                drawPath(
                    path = messageTrianglePath,
                    color = messageColor
                )
            }
            .background(
                messageColor,
                shape = RoundedCornerShape(dimensionResource(id = R.dimen._15dp))
            )
            .padding(dimensionResource(id = R.dimen._5dp))
    ) {
        Text(
            text = message.username,
            fontWeight = FontWeight.Bold
        )
        Text(text = message.text)
        Text(
            text = message.formattedTime,
            textAlign = TextAlign.End
        )
    }
}

private fun DrawScope.messageTrianglePath(isOwnMessage: Boolean): Path {
    val cornerRadius = 20.dp.toPx()
    val triangleHeight = 10.dp.toPx()
    val triangleWidth = 15.dp.toPx()
    return Path().apply {
        if (isOwnMessage) {
            moveTo(size.width, size.height - cornerRadius)
            lineTo(size.width, size.height + triangleHeight)
            lineTo(size.width - triangleWidth, size.height - cornerRadius)
            close()
        } else {
            moveTo(0f, size.height - cornerRadius)
            lineTo(0f, size.height + triangleHeight)
            lineTo(triangleWidth, size.height - cornerRadius)
            close()
        }
    }
}