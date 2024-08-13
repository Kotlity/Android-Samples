package com.kotlity.services.ui.composables

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.kotlity.services.R

@Composable
fun SiteTextField(
    modifier: Modifier = Modifier,
    siteText: String,
    placeholderText: String = stringResource(id = R.string.siteTextFieldPlaceholderText),
    isError: Boolean = false,
    supportingText: String? = null,
    onSiteTextChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier,
        value = siteText,
        onValueChange = onSiteTextChange,
        placeholder = {
            Text(text = placeholderText)
        },
        isError = isError,
        supportingText = {
            supportingText?.let {
                Text(text = it)
            }
        }
    )
}