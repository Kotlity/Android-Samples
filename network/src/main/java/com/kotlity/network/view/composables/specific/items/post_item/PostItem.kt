package com.kotlity.network.view.composables.specific.items.post_item

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.kotlity.network.R
import com.kotlity.network.model.data.Post

@Composable
fun PostItem(
    modifier: Modifier = Modifier,
    post: Post
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.userId, post.userId),
            fontWeight = FontWeight.Bold
        )
        Text(text = stringResource(R.string.id, post.id))
        Text(
            text = stringResource(R.string.title, post.title),
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = stringResource(R.string.body, post.body),
            fontStyle = FontStyle.Italic
        )
    }
}