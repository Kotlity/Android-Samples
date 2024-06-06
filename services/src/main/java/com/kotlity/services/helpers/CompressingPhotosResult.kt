package com.kotlity.services.helpers

sealed class CompressingPhotosResult(val result: String) {
    data class Success(val compressedPhotoFilePath: String): CompressingPhotosResult(result = compressedPhotoFilePath)
    data class Error(val errorMessage: String): CompressingPhotosResult(result = errorMessage)
}