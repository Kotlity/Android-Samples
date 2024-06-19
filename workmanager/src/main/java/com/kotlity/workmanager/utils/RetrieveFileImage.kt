package com.kotlity.workmanager.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory

fun ByteArray.retrieveFileImage(): Bitmap? {
    return try {
        BitmapFactory.decodeByteArray(this, 0, size)
    } catch (e: Exception) {
        e.localizedMessage?.let { CustomException(it) }
        null
    }
}