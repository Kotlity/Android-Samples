package com.kotlity.services.compressing_photos

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.kotlity.services.R
import com.kotlity.services.file.FileCompressionHelper
import com.kotlity.services.helpers.CompressingPhotosResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import kotlin.math.roundToInt

class ForegroundCompressingPhotosHelper(
    private val context: Context,
    private val fileCompressionHelper: FileCompressionHelper
): CompressingPhotosHelper {

    override var imageQuality: Int = 100

    override suspend fun compressImage(stringImagePath: String, compressionImageLimit: Long): CompressingPhotosResult {
        return withContext(Dispatchers.IO) {
            val imageUri = Uri.parse(stringImagePath)
            val imageBytesArray = context.contentResolver.openInputStream(imageUri)?.use { inputStream ->
                inputStream.readBytes()
            } ?: return@withContext CompressingPhotosResult.Error(errorMessage = context.getString(R.string.compressingImageError))
            val bitmap = BitmapFactory.decodeByteArray(imageBytesArray, 0, imageBytesArray.size)
            var bitmapCompressedBytesArray: ByteArray
            do {
                val bitmapCompressedByteArrayOutputStream = ByteArrayOutputStream()
                bitmapCompressedByteArrayOutputStream.use { outputStream ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, imageQuality, outputStream)
                    bitmapCompressedBytesArray = outputStream.toByteArray()
                    imageQuality -= (imageQuality * 0.1).roundToInt()
                }
            } while (bitmapCompressedBytesArray.size >= compressionImageLimit && imageQuality > 5)
            val compressedFileName = System.currentTimeMillis().toString().plus(".jpg")
            val compressedFileAbsolutePath = fileCompressionHelper.writeBytesToFile(
                fileName = compressedFileName,
                outputBytes = bitmapCompressedBytesArray
            )
            CompressingPhotosResult.Success(result = compressedFileAbsolutePath)
        }
    }
}