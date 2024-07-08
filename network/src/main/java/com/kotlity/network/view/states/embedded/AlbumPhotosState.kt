package com.kotlity.network.view.states.embedded

import android.os.Parcelable
import com.kotlity.network.view.states.embedded.data.AlbumPhotosData
import kotlinx.parcelize.Parcelize

@Parcelize
data class AlbumPhotosState(
    val dropDownMenuState: DropdownMenuState = DropdownMenuState(),
    val albumPhotosData: AlbumPhotosData = AlbumPhotosData()
): Parcelable
