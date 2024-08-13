package com.kotlity.services.viewmodels

import android.net.Uri
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlity.services.decoder.BitmapDecoder
import com.kotlity.services.events.PhotosCompressingEvent
import com.kotlity.services.helpers.ErrorType
import com.kotlity.services.helpers.ValidatorState
import com.kotlity.services.helpers.asUiText
import com.kotlity.services.launcher.Launcher
import com.kotlity.services.states.PhotosCompressingState
import com.kotlity.services.states.PhotosSiteUrlValidatorState
import com.kotlity.services.validator.TextValidator
import kotlinx.coroutines.launch

class PhotosCompressingViewModel(
    private val siteUrlValidator: TextValidator,
    private val launcher: Launcher,
    private val bitmapDecoder: BitmapDecoder
): ViewModel() {

    var photosCompressingState by mutableStateOf(PhotosCompressingState())
        private set

    var siteUrlValidatorState by derivedStateOf {
        mutableStateOf<PhotosSiteUrlValidatorState>(PhotosSiteUrlValidatorState.Undefined)
    }.value
        private set

    fun onEvent(photosCompressingEvent: PhotosCompressingEvent) {
        when(photosCompressingEvent) {
            is PhotosCompressingEvent.OnSiteUrlChange -> onSiteUrlChange(photosCompressingEvent.siteUrl)
            PhotosCompressingEvent.OnLaunchSite -> onLaunchSite()
            is PhotosCompressingEvent.OnUncompressedPhotoUriReceived -> onUncompressedPhotoUriReceived(photosCompressingEvent.photoUri)
            is PhotosCompressingEvent.OnCompressedPhotoFilePathReceived -> onCompressedPhotoFilePathReceived(photosCompressingEvent.compressedPhotoFilePath)
            PhotosCompressingEvent.OnCompressingUpdate -> onCompressingUpdate()
        }
    }

    private fun updateSiteUrlValidatorState(photosSiteUrlValidatorState: PhotosSiteUrlValidatorState) {
        siteUrlValidatorState = photosSiteUrlValidatorState
    }

    private fun handleValidatorState(validatorState: ValidatorState) {
        when (validatorState) {
            is ValidatorState.Error -> when (validatorState.errorType) {
                ErrorType.SiteUrlError.SITE_URL_IS_BLANK -> updateSiteUrlValidatorState(PhotosSiteUrlValidatorState.Error(errorMessage = validatorState.errorType.asUiText()))
                ErrorType.SiteUrlError.INCORRECT_SITE_URL_FORMAT -> updateSiteUrlValidatorState(PhotosSiteUrlValidatorState.Error(errorMessage = validatorState.errorType.asUiText()))
            }

            ValidatorState.Success -> updateSiteUrlValidatorState(PhotosSiteUrlValidatorState.Success)
            ValidatorState.Undefined -> return
        }
    }

    private fun onSiteUrlChange(siteUrl: String) {
        photosCompressingState = photosCompressingState.copy(siteUrl = siteUrl)
        val validatorState = siteUrlValidator.validateText(siteUrl)
        handleValidatorState(validatorState)
    }

    private fun onLaunchSite() {
        launcher.launch(photosCompressingState.siteUrl)
    }

    private fun onCompressingUpdate() {
        val isPhotoCompressing = photosCompressingState.isCompressing
        photosCompressingState = photosCompressingState.copy(isCompressing = !isPhotoCompressing)
    }

    private fun onUncompressedPhotoUriReceived(photoUri: Uri) {
        photosCompressingState = photosCompressingState.copy(uncompressedPhotoUri = photoUri)
    }

    private fun onCompressedPhotoFilePathReceived(compressedPhotoFilePath: String) {
        viewModelScope.launch {
            bitmapDecoder.decodeBitmap(compressedPhotoFilePath)?.let {
                photosCompressingState = photosCompressingState.copy(compressedBitmap = it)
            }
        }
    }

}