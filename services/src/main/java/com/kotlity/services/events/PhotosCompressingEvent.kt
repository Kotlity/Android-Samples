package com.kotlity.services.events

import android.net.Uri

sealed interface PhotosCompressingEvent {
    data class OnSiteUrlChange(val siteUrl: String): PhotosCompressingEvent
    data class OnUncompressedPhotoUriReceived(val photoUri: Uri): PhotosCompressingEvent
    data class OnCompressedPhotoFilePathReceived(val compressedPhotoFilePath: String): PhotosCompressingEvent
    data object OnLaunchSite: PhotosCompressingEvent
    data object OnCompressingUpdate: PhotosCompressingEvent
}