package com.kotlity.network.view.composables.specific.items.post_comments_item

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.dimensionResource
import com.kotlity.network.R
import com.kotlity.network.model.data.PostComment

@Composable
fun PostCommentsItemContent(
    modifier: Modifier = Modifier,
    postComment: PostComment,
    shape: Shape = CardDefaults.shape,
    colors: CardColors = CardDefaults.cardColors(),
    elevation: CardElevation = CardDefaults.cardElevation(),
    border: BorderStroke? = null,
) {
    Card(
        modifier = modifier,
        shape = shape,
        colors = colors,
        elevation = elevation,
        border = border
    ) {
        PostCommentsItem(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen._2dp)),
            postComment = postComment
        )
    }
}