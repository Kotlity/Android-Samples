package com.kotlity.network.view.states.embedded.data

import android.os.Parcelable
import com.kotlity.network.model.data.PostComment
import com.kotlity.network.utils.UiText
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostCommentsData(
    val arePostCommentsLoading: Boolean = false,
    val postComments: List<PostComment> = emptyList(),
    val uiText: UiText? = null
): Parcelable
