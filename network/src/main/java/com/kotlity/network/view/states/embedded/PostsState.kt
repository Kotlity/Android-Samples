package com.kotlity.network.view.states.embedded

import android.os.Parcelable
import com.kotlity.network.model.data.Post
import com.kotlity.network.utils.UiText
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostsState(
    val arePostsLoading: Boolean = false,
    val posts: List<Post> = emptyList(),
    val uiText: UiText? = null
): Parcelable