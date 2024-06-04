package com.kotlity.services.helpers

sealed class CompressingPhotosResult(val result: String) {
    class Success(result: String): CompressingPhotosResult(result = result)
    class Error(errorMessage: String): CompressingPhotosResult(result = errorMessage)
}