package com.kotlity.network.utils

import android.content.Context
import android.os.Parcelable
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import kotlinx.parcelize.Parcelize

sealed interface UiText: Parcelable {
    @Parcelize
    data class DynamicString(val value: String): UiText
    @Parcelize
    class StringResource(
        @StringRes val id: Int
    ): UiText

    @Composable
    fun asComposeString(): String {
        return when(this) {
            is DynamicString -> value
            is StringResource -> LocalContext.current.getString(id)
        }
    }

    fun asString(context: Context): String {
        return when(this) {
            is DynamicString -> value
            is StringResource -> context.getString(id)
        }
    }
}