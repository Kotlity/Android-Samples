package com.kotlity.workmanager.repositories.file

interface MetadataRetriever {

    fun retrieveData(fileUrl: String, metadataKey: Int): String?

    fun retrieveImageByteArray(fileUrl: String): ByteArray?
}