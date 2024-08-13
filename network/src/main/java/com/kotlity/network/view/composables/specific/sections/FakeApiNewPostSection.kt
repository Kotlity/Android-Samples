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
import com.kotlity.network.view.states.embedded.NewPostState

@Composable
fun FakeApiNewPostSection(
    newPostState: NewPostState,
    onUserIdChange: (String) -> Unit,
    onUserIdClearIconClick: () -> Unit,
    onTitleChange: (String) -> Unit,
    onTitleClearIconClick: () -> Unit,
    onBodyChange: (String) -> Unit,
    onBodyClearIconClick: () -> Unit,
    onCreateNewPostButtonClick: () -> Unit
) {

    val softwareKeyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val defaultEditablePost = newPostState.defaultEditablePost
    val isCreateNewPostButtonEnabled = defaultEditablePost.post.userId >= 1 && defaultEditablePost.post.title.isNotBlank() && defaultEditablePost.post.body.isNotBlank()

    FakeApiOutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = defaultEditablePost.post.userId.toString(),
        onValueChange = onUserIdChange,
        label = stringResource(R.string.userIdLabel),
        onTrailingIconClick = onUserIdClearIconClick,
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
        targetState = newPostState.newPostData,
        content = { newPostData ->
            newPostData.post?.let { newPost ->
                PostItemContent(
                    modifier = Modifier.fillMaxWidth(),
                    post = newPost
                )
            }
            newPostData.uiText?.let { uiText ->
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
        onClick = onCreateNewPostButtonClick,
        enabled = isCreateNewPostButtonEnabled,
        title = {
            Text(
                text = stringResource(R.string.createNewPost),
                fontWeight = FontWeight.Bold
            )
        }
    )
}