package com.kotlity.services.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kotlity.services.decoder.BitmapDecoder
import com.kotlity.services.launcher.Launcher
import com.kotlity.services.validator.TextValidator

class PhotosCompressingViewModelFactory(
    private val siteUrlValidator: TextValidator,
    private val launcher: Launcher,
    private val bitmapDecoder: BitmapDecoder
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PhotosCompressingViewModel(
            siteUrlValidator,
            launcher,
            bitmapDecoder
        ) as T
    }
}