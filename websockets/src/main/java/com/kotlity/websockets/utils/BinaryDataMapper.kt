package com.kotlity.websockets.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory

fun List<ByteArray>.toBitmaps(): List<Bitmap> {
    return this.mapNotNull { byteArray ->
        try {
            BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}

private fun ByteArray.toBitmap(): Bitmap? {
    return try {
        BitmapFactory.decodeByteArray(this, 0, size)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun Map<String, ByteArray?>.toBitmapMap(): Map<String, Bitmap?> {
    return try {
        mapValues { it.value?.toBitmap() }
    } catch (e: Exception) {
        e.printStackTrace()
        emptyMap()
    }
}