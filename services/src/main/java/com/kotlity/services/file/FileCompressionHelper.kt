package com.kotlity.services.file

interface FileCompressionHelper {

    suspend fun writeBytesToFile(fileName: String, outputBytes: ByteArray): String
}