package com.kotlity.websockets.utils

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals

data class CustomSnackbarVisuals(
    private val labelAction: String? = null,
    private val snackbarDuration: SnackbarDuration = SnackbarDuration.Short,
    override val message: String,
    private val dismissAction: Boolean = true
): SnackbarVisuals {

    override val actionLabel: String?
        get() = labelAction

    override val duration: SnackbarDuration
        get() = snackbarDuration

    override val withDismissAction: Boolean
        get() = dismissAction


}