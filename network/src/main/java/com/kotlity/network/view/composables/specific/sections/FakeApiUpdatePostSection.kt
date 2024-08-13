package com.kotlity.network.view.composables.specific.sections

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.kotlity.network.R
import com.kotlity.network.utils.Constants
import com.kotlity.network.view.composables.generic.FakeApiAnimatedContent
import com.kotlity.network.view.composables.generic.FakeApiOutlinedButton
import com.kotlity.network.view.composables.generic.FakeApiOutlinedTextField
import com.kotlity.network.view.composables.specific.items.post_item.PostItemContent
import com.kotlity.network.view.states.embedded.UpdatedPostState

@Composable
fun FakeApiUpdatePostSection(
    updatedPostState: UpdatedPostState,
    onUserIdChange: (String) -> Unit,
    onUserIdClearIconClick: () -> Unit,
    onIdChange: (String) -> Unit,
    onIdClearIconClick: () -> Unit,
    onTitleChange: (String) -> Unit,
    onTitleClearIconClick: () -> Unit,
    onBodyChange: (String) -> Unit,
    onBodyClearIconClick: () -> Unit,
    onUpdatePostButtonClick: () -> Unit
) {

    val softwareKeyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val defaultEditablePost = updatedPostState.defaultEditablePost
    val isUpdatePostButtonEnabled = defaultEditablePost.post.userId >= 1 && defaultEditablePost.post.id >= 1 &&
            defaultEditablePost.post.title.isNotBlank() && defaultEditablePost.post.body.isNotBlank()

    FakeApiOutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = defaultEditablePost.post.userId.toString(),
        onValueChange = onUserIdChange,
        label = stringResource(R.string.userIdLabel),
        onTrailingIconClick = onUserIdClearIconClick,
        keyboardType = KeyboardType.Number
    )
    FakeApiOutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = defaultEditablePost.post.id.toString(),
        onValueChange = onIdChange,
        label = stringResource(R.string.idLabel),
        onTrailingIconClick = onIdClearIconClick,
        keyboardType = KeyboardType.Number
    )
    Spacer(modifier = Modifier.height(dimensionResource(R.dimen._5dp)))
    FakeApiOutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = defaultEditablePost.post.title,
        onValueChange = onTitleChange,
        label = stringResource(R.string.titleLabel),
        onTrailingIconClick = onTitleClearIconClick
    )
    Spacer(modifier = Modifier.height(dimensionResource(R.dimen._5dp)))
    FakeApiOutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = defaultEditablePost.post.body,
        onValueChange = onBodyChange,
        label = stringResource(R.string.bodyLabel),
        onTrailingIconClick = onBodyClearIconClick,
        imeAction = ImeAction.Done,
        singleLine = false,
        onKeyboardDoneButtonClick = {
            softwareKeyboardController?.hide()
            focusManager.clearFocus()
        }
    )
    Spacer(modifier = Modifier.height(dimensionResource(R.dimen._5dp)))
    FakeApiAnimatedContent(
        targetState = updatedPostState.postData,
        content = { updatedPost ->
            updatedPost.post?.let {
                PostItemContent(
                    modifier = Modifier.fillMaxWidth(),
                    post = it
                )
            }
            updatedPost.uiText?.let { uiText ->
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
        onClick = onUpdatePostButtonClick,
        enabled = isUpdatePostButtonEnabled,
        title = {
            Text(
                text = stringResource(R.string.updatePostById),
                fontWeight = FontWeight.Bold
            )
        }
    )
}