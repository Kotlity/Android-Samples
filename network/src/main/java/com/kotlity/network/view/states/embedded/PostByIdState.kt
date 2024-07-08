package com.kotlity.network.view.states.embedded

import android.os.Parcelable
import com.kotlity.network.view.states.embedded.data.PostData
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostByIdState(
    val dropdownMenuState: DropdownMenuState = DropdownMenuState(),
    val postData: PostData = PostData()
): Parcelable