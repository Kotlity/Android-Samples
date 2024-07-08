package com.kotlity.network.view.composables.specific.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
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
import com.kotlity.network.view.composables.generic.FakeApiOutlinedButton
import com.kotlity.network.view.composables.specific.items.post_item.PostItem
import com.kotlity.network.view.states.embedded.PostByIdState

@Composable
fun FakeApiPostByIdSection(
    postByIdState: PostByIdState,
    onExpandedChange: (Boolean) -> Unit,
    onDismissDropdownMenuRequest: () -> Unit,
    onPostByIdItemClicked: (Int) -> Unit,
    getPostByIdButtonClick: () -> Unit
) {
    FakeApiAnimatedContent(
        targetState = postByIdState.postData,
        content = { postData ->
            postData.post?.let { post ->
                PostItem(
                    modifier = Modifier.fillMaxWidth(),
                    post = post
                )
            }
            postData.uiText?.let { uiText ->
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
            expanded = postByIdState.dropdownMenuState.isDropdownMenuExpanded,
            onExpandedChange = onExpandedChange,
            label = stringResource(R.string.postLabel),
            currentSelectedItem = postByIdState.dropdownMenuState.id,
            onDismissRequest = onDismissDropdownMenuRequest,
            itemsList = (1..100).toList(),
            onItemClicked = onPostByIdItemClicked,
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
            onClick = getPostByIdButtonClick,
            title = {
                Text(
                    text = stringResource(R.string.getPostById),
                    fontWeight = FontWeight.Bold
                )
            }
        )
    }
}