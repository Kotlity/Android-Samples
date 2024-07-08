package com.kotlity.network.view.states.embedded.data

import android.os.Parcelable
import com.kotlity.network.utils.UiText
import kotlinx.parcelize.Parcelize

@Parcelize
data class DeletePostData(
    val isPostSuccessfullyDeleted: Boolean = false,
    val uiText: UiText? = null
): Parcelable
