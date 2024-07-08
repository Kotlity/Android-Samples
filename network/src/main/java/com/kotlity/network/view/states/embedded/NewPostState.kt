package com.kotlity.network.view.states.embedded

import android.os.Parcelable
import com.kotlity.network.view.states.DefaultEditablePost
import com.kotlity.network.view.states.embedded.data.PostData
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewPostState(
    val defaultEditablePost: DefaultEditablePost = DefaultEditablePost(),
    val newPostData: PostData = PostData()
): Parcelable