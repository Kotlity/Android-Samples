package com.kotlity.network.view.composables.specific.sections

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.kotlity.network.R
import com.kotlity.network.utils.Constants
import com.kotlity.network.view.composables.generic.FakeApiAnimatedContent
import com.kotlity.network.view.composables.generic.FakeApiLazyColumn
import com.kotlity.network.view.composables.generic.FakeApiOutlinedButton
import com.kotlity.network.view.composables.specific.items.post_item.PostItemContent
import com.kotlity.network.view.states.embedded.PostsState

@Composable
fun ColumnScope.FakeApiPostsSection(
    postsState: PostsState,
    getAllPostsButtonClick: () -> Unit
) {
    FakeApiAnimatedContent(
        targetState = postsState,
        content = { postsState ->
            if (postsState.posts.isNotEmpty()) {
                FakeApiLazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(dimensionResource(R.dimen._200dp)),
                    itemsList = postsState.posts,
                    key = { post -> post.id },
                    itemContent = { post ->
                        PostItemContent(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = dimensionResource(R.dimen._5dp)),
                            post = post
                        )
                    }
                )
            }
            if (postsState.arePostsLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            postsState.uiText?.let { uiText ->
                Text(
                    text = uiText.asComposeString(),
                    fontWeight = FontWeight.Bold,
                    fontSize = Constants._18sp
                )
            }
        }
    )
    Spacer(modifier = Modifier.height(dimensionResource(R.dimen._5dp)))
    FakeApiOutlinedButton(
        modifier = Modifier.fillMaxWidth(),
        onClick = getAllPostsButtonClick,
        enabled = !postsState.arePostsLoading,
        title = {
            Text(
                text = stringResource(R.string.getAllPosts),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = Constants._16sp
                )
            )
        }
    )
}