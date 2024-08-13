package com.kotlity.websockets.presentation.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.kotlity.websockets.R
import com.kotlity.websockets.utils.CustomSnackbarVisuals

@Composable
fun ChatSnackbarHost(
    snackbarHostState: SnackbarHostState
) {
    SnackbarHost(hostState = snackbarHostState) { data ->
        val customSnackbarVisuals = data.visuals as CustomSnackbarVisuals
        val isShowDismissAction = customSnackbarVisuals.withDismissAction
        Snackbar(
            modifier = Modifier.padding(dimensionResource(id = R.dimen._20dp)),
            dismissAction = {
                if (isShowDismissAction) {
                    TextButton(onClick = { data.dismiss() }) {
                        Text(text = stringResource(id = R.string.labelDismiss))
                    }
                }
            },
            content = {
                Text(
                    text = customSnackbarVisuals.message,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
        )
    }
}