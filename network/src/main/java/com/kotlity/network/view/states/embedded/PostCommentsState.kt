package com.kotlity.network.view.states.embedded

import android.os.Parcelable
import com.kotlity.network.view.states.embedded.data.PostCommentsData
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostCommentsState(
    val dropdownMenuState: DropdownMenuState = DropdownMenuState(),
    val postCommentsData: PostCommentsData = PostCommentsData()
): Parcelable
