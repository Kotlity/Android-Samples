package com.kotlity.network.view.states.embedded

import android.os.Parcelable
import com.kotlity.network.view.states.DefaultEditablePost
import com.kotlity.network.view.states.embedded.data.PostData
import kotlinx.parcelize.Parcelize

@Parcelize
data class UpdatedPostState(
    val defaultEditablePost: DefaultEditablePost = DefaultEditablePost(),
    val postData: PostData = PostData()
): Parcelable