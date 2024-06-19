package com.kotlity.workmanager.repositories.file

interface FileDownloader {

    suspend fun downloadFile(fileUrl: String, fileName: String): Boolean
}