package com.kotlity.network.model.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostComment(
    val postId: Int,
    val id: Int,
    val name: String,
    val email: String,
    val body: String
): Parcelable