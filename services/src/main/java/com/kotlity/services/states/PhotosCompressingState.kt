package com.kotlity.services.states

import android.graphics.Bitmap
import android.net.Uri
import com.kotlity.services.helpers.UiText

data class PhotosCompressingState(
    val siteUrl: String = "",
    val uncompressedPhotoUri: Uri? = null,
    val compressedBitmap: Bitmap? = null,
    val isCompressing: Boolean = false,
    val siteUrlErrorMessage: UiText? = null
)