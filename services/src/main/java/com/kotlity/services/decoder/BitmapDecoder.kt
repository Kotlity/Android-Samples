package com.kotlity.services.decoder

import android.graphics.Bitmap

interface BitmapDecoder {

    suspend fun decodeBitmap(filePath: String): Bitmap?
}