package com.kotlity.network.view.states.embedded.data

import android.os.Parcelable
import com.kotlity.network.model.data.AlbumPhoto
import com.kotlity.network.utils.UiText
import kotlinx.parcelize.Parcelize

@Parcelize
data class AlbumPhotosData(
    val areAlbumPhotosLoading: Boolean = false,
    val albumPhotos: List<AlbumPhoto> = emptyList(),
    val uiText: UiText? = null
): Parcelable
