package com.kotlity.network.view.composables.generic

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun FakeApiOutlinedTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String = "",
    onTrailingIconClick: () -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    singleLine: Boolean = true,
    focusRequester: FocusRequester = FocusRequester.Default,
    onKeyboardDoneButtonClick: ((KeyboardActionScope.() -> Unit))? = null
) {
    OutlinedTextField(
        modifier = modifier.focusRequester(focusRequester),
        value = value,
        onValueChange = onValueChange,
        label = {
            if (label.isNotEmpty()) {
                Text(text = label)
            }
        },
        trailingIcon = {
            UpdateTrailingIcon(
                isInputTextEmpty = value.isBlank(),
                onTrailingIconClick = onTrailingIconClick
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(
            onDone = if (imeAction == ImeAction.Done) onKeyboardDoneButtonClick else null
        ),
        singleLine = singleLine
    )
}

@Composable
private fun UpdateTrailingIcon(
    isInputTextEmpty: Boolean,
    onTrailingIconClick: () -> Unit
) {
    AnimatedContent(targetState = isInputTextEmpty, label = "") { isEmpty ->
        if (!isEmpty) {
            Icon(
                modifier = Modifier.clickable { onTrailingIconClick() },
                imageVector = Icons.Default.Delete,
                contentDescription = null
            )
        }
    }
}