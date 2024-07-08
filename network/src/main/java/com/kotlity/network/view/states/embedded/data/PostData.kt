package com.kotlity.network.view.states.embedded.data

import android.os.Parcelable
import com.kotlity.network.model.data.Post
import com.kotlity.network.utils.UiText
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostData(
    val post: Post? = null,
    val uiText: UiText? = null
): Parcelable
