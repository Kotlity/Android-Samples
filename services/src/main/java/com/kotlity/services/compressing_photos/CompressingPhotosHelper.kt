package com.kotlity.services.compressing_photos

import com.kotlity.services.helpers.CompressingPhotosResult

interface CompressingPhotosHelper {

    val imageQuality: Int

    suspend fun compressImage(stringImagePath: String, compressionImageLimit: Long): CompressingPhotosResult
}