package com.kotlity.network.view.states

import android.os.Parcelable
import com.kotlity.network.model.data.Post
import kotlinx.parcelize.Parcelize

@Parcelize
data class DefaultEditablePost(
    val post: Post = Post(
        userId = 1,
        id = 101,
        title = "",
        body = ""
    )
): Parcelable
