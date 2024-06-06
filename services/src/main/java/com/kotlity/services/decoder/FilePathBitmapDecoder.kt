package com.kotlity.services.decoder

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FilePathBitmapDecoder: BitmapDecoder {

    override suspend fun decodeBitmap(filePath: String): Bitmap? {
        return withContext(Dispatchers.IO) {
            BitmapFactory.decodeFile(filePath)
        }
    }
}