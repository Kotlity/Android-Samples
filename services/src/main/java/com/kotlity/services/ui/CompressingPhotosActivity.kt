package com.kotlity.services.ui

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.kotlity.services.ui.theme.ServicesTheme
import com.kotlity.services.CompressingPhotosService
import com.kotlity.services.decoder.FilePathBitmapDecoder
import com.kotlity.services.events.PhotosCompressingEvent
import com.kotlity.services.helpers.CompressingPhotoServiceActions
import com.kotlity.services.helpers.Constants.DEFAULT_PHOTO_COMPRESSION_IMAGE_LIMIT
import com.kotlity.services.helpers.Constants.PHOTO_COMPRESSION_IMAGE_LIMIT
import com.kotlity.services.helpers.Constants.PHOTO_COMPRESSION_IMAGE_PATH
import com.kotlity.services.launcher.Launcher
import com.kotlity.services.launcher.SiteLauncher
import com.kotlity.services.ui.screens.PhotosCompressingScreen
import com.kotlity.services.validator.IsCorrectSiteUrlFormatValidator
import com.kotlity.services.validator.IsTextBlankValidator
import com.kotlity.services.validator.SiteUrlValidator
import com.kotlity.services.validator.TextValidator
import com.kotlity.services.viewmodels.PhotosCompressingViewModel
import com.kotlity.services.viewmodels.PhotosCompressingViewModelFactory

class CompressingPhotosActivity : ComponentActivity() {

    private val textValidator: TextValidator = SiteUrlValidator(
        isTextBlankValidator = IsTextBlankValidator(),
        isCorrectSiteUrlFormatValidator = IsCorrectSiteUrlFormatValidator()
    )
    private val launcher: Launcher = SiteLauncher(this)
    private val bitmapDecoder = FilePathBitmapDecoder()

    private val photosCompressingViewModel: PhotosCompressingViewModel by viewModels {
        PhotosCompressingViewModelFactory(
            siteUrlValidator =
            textValidator,
            launcher = launcher,
            bitmapDecoder = bitmapDecoder
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val photosCompressingState = photosCompressingViewModel.photosCompressingState
            val siteUrlValidatorState = photosCompressingViewModel.siteUrlValidatorState
            ServicesTheme {
                PhotosCompressingScreen(
                    photosCompressingState = photosCompressingState,
                    siteUrlValidatorState = siteUrlValidatorState,
                    onEvent = photosCompressingViewModel::onEvent
                )
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val photoUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(Intent.EXTRA_STREAM, Uri::class.java)
        } else {
            intent.getParcelableExtra(Intent.EXTRA_STREAM)
        } ?: return

        photosCompressingViewModel.apply {
            onEvent(PhotosCompressingEvent.OnSiteUrlChange(""))
            onEvent(PhotosCompressingEvent.OnUncompressedPhotoUriReceived(photoUri = photoUri))
        }

        val compressingPhotosIntent = Intent(this, CompressingPhotosService::class.java).apply {
            putExtra(PHOTO_COMPRESSION_IMAGE_PATH, photoUri.toString())
            putExtra(PHOTO_COMPRESSION_IMAGE_LIMIT, DEFAULT_PHOTO_COMPRESSION_IMAGE_LIMIT)
            action = CompressingPhotoServiceActions.START.toString()
        }
        startService(compressingPhotosIntent)

        photosCompressingViewModel.onEvent(PhotosCompressingEvent.OnCompressingUpdate)
    }
}