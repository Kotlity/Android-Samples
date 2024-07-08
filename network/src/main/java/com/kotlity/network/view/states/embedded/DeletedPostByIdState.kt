package com.kotlity.network.view.states.embedded

import android.os.Parcelable
import com.kotlity.network.view.states.embedded.data.DeletePostData
import kotlinx.parcelize.Parcelize

@Parcelize
data class DeletedPostByIdState(
    val dropdownMenuState: DropdownMenuState = DropdownMenuState(),
    val deletePostData: DeletePostData = DeletePostData()
): Parcelable