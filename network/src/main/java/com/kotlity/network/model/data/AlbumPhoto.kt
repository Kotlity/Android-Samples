package com.kotlity.network.model.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AlbumPhoto(
    val albumId: Int,
    val id: Int,
    val title: String,
    val url: String,
): Parcelable