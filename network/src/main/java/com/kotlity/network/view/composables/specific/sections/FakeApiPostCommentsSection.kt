package com.kotlity.network.view.composables.specific.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
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
import com.kotlity.network.view.composables.generic.FakeApiExposedDropdownMenu
import com.kotlity.network.view.composables.generic.FakeApiLazyColumn
import com.kotlity.network.view.composables.generic.FakeApiOutlinedButton
import com.kotlity.network.view.composables.specific.items.post_comments_item.PostCommentsItemContent
import com.kotlity.network.view.states.embedded.PostCommentsState

@Composable
fun ColumnScope.FakeApiPostCommentsSection(
    postCommentsState: PostCommentsState,
    onExpandedChange: (Boolean) -> Unit,
    onDismissDropdownMenuRequest: () -> Unit,
    onIdItemClicked: (Int) -> Unit,
    getPostCommentsByIdButtonClick: () -> Unit
) {
    FakeApiAnimatedContent(
        targetState = postCommentsState.postCommentsData,
        content = { postCommentsData ->
            if (postCommentsData.postComments.isNotEmpty()) {
                FakeApiLazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(dimensionResource(R.dimen._200dp)),
                    itemsList = postCommentsState.postCommentsData.postComments,
                    key = { post -> post.id },
                    itemContent = { postComment ->
                        PostCommentsItemContent(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = dimensionResource(R.dimen._5dp)),
                            postComment = postComment
                        )
                    }
                )
            }
            if (postCommentsData.arePostCommentsLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            postCommentsData.uiText?.let { uiText ->
                Text(
                    text = uiText.asComposeString(),
                    fontWeight = FontWeight.Bold,
                    fontSize = Constants._18sp
                )
            }
        }
    )
    Spacer(modifier = Modifier.height(dimensionResource(R.dimen._5dp)))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        FakeApiExposedDropdownMenu(
            modifier = Modifier.weight(0.4f),
            expanded = postCommentsState.dropdownMenuState.isDropdownMenuExpanded,
            onExpandedChange = onExpandedChange,
            label = stringResource(R.string.postLabel),
            currentSelectedItem = postCommentsState.dropdownMenuState.id,
            onDismissRequest = onDismissDropdownMenuRequest,
            itemsList = (1..100).toList(),
            onItemClicked = onIdItemClicked,
            itemText = { itemId ->
                Text(
                    text = itemId.toString(),
                    fontWeight = FontWeight.SemiBold
                )
            }
        )
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen._10dp)))
        FakeApiOutlinedButton(
            modifier = Modifier.weight(0.4f),
            onClick = getPostCommentsByIdButtonClick,
            title = {
                Text(
                    text = stringResource(R.string.getPostCommentsById),
                    fontWeight = FontWeight.Bold
                )
            }
        )
    }
}