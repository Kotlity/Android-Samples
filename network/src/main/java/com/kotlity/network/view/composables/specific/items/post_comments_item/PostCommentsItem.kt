package com.kotlity.network.view.composables.specific.items.post_comments_item

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.kotlity.network.R
import com.kotlity.network.model.data.PostComment

@Composable
fun PostCommentsItem(
    modifier: Modifier = Modifier,
    postComment: PostComment
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.postId, postComment.postId),
            fontWeight = FontWeight.Bold
        )
        Text(text = stringResource(R.string.id, postComment.id))
        Text(
            text = stringResource(R.string.name, postComment.name),
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = stringResource(R.string.email, postComment.email),
            fontWeight = FontWeight.SemiBold,
            fontStyle = FontStyle.Italic
        )
        Text(
            text = stringResource(R.string.body, postComment.body),
            fontStyle = FontStyle.Italic
        )
    }
}