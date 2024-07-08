package com.kotlity.network.view.states.embedded

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DropdownMenuState(
    val id: Int = 1,
    val isDropdownMenuExpanded: Boolean = false
): Parcelable
