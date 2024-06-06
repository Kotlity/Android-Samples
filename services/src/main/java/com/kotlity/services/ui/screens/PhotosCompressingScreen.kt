package com.kotlity.services.ui.screens

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.core.content.ContextCompat
import com.kotlity.services.CompressingPhotosResultBroadcastReceiver
import com.kotlity.services.CompressingPhotosService
import com.kotlity.services.R
import com.kotlity.services.events.PhotosCompressingEvent
import com.kotlity.services.helpers.CompressingPhotoServiceActions
import com.kotlity.services.helpers.Constants.FloatConstants._05f
import com.kotlity.services.helpers.Constants.FloatConstants._07f
import com.kotlity.services.helpers.Constants.PHOTO_COMPRESSION_ACTION_RESULT
import com.kotlity.services.states.PhotosCompressingState
import com.kotlity.services.states.PhotosSiteUrlValidatorState
import com.kotlity.services.ui.composables.PhotoLoader
import com.kotlity.services.ui.composables.SiteLauncherButton
import com.kotlity.services.ui.composables.SiteTextField

@Composable
fun PhotosCompressingScreen(
    photosCompressingState: PhotosCompressingState,
    siteUrlValidatorState: PhotosSiteUrlValidatorState,
    onEvent: (PhotosCompressingEvent) -> Unit
) {
    val context = LocalContext.current
    val postNotificationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isPermissionGranted ->
            if (isPermissionGranted) onEvent(PhotosCompressingEvent.OnLaunchSite)
            else Toast.makeText(context, context.getString(R.string.postNotificationPermissionNotGranted), Toast.LENGTH_SHORT).show()
        }
    )
    val compressingPhotosResultBroadcastReceiver by rememberUpdatedState(newValue = CompressingPhotosResultBroadcastReceiver { compressingPhotoResult ->
        when(compressingPhotoResult) {
            context.getString(R.string.compressingImageError) -> {
                Toast.makeText(context, compressingPhotoResult, Toast.LENGTH_SHORT).show()
                onEvent(PhotosCompressingEvent.OnCompressingUpdate)
                val compressingPhotosIntent = Intent(context, CompressingPhotosService::class.java).apply {
                    action = CompressingPhotoServiceActions.STOP.toString()
                }
                context.startService(compressingPhotosIntent)
            }
            else -> {
                onEvent(PhotosCompressingEvent.OnCompressedPhotoFilePathReceived(compressedPhotoFilePath = compressingPhotoResult))
                onEvent(PhotosCompressingEvent.OnCompressingUpdate)
                val compressingPhotosIntent = Intent(context, CompressingPhotosService::class.java).apply {
                    action = CompressingPhotoServiceActions.STOP.toString()
                }
                context.startService(compressingPhotosIntent)
            }
        }
    })
    
    DisposableEffect(key1 = context) {
        val intentFilter = IntentFilter(PHOTO_COMPRESSION_ACTION_RESULT)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.registerReceiver(compressingPhotosResultBroadcastReceiver, intentFilter, Context.RECEIVER_NOT_EXPORTED)
        } else context.registerReceiver(compressingPhotosResultBroadcastReceiver, intentFilter)

        onDispose { context.unregisterReceiver(compressingPhotosResultBroadcastReceiver) }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        SiteTextField(
            modifier = Modifier.fillMaxWidth(_07f),
            siteText = photosCompressingState.siteUrl,
            isError = siteUrlValidatorState is PhotosSiteUrlValidatorState.Error,
            supportingText = if (siteUrlValidatorState is PhotosSiteUrlValidatorState.Error) siteUrlValidatorState.errorMessage.asComposeString() else null,
            onSiteTextChange = {
                onEvent(PhotosCompressingEvent.OnSiteUrlChange(siteUrl = it))
            }
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen._10dp)))
        SiteLauncherButton(
            modifier = Modifier.fillMaxWidth(_05f),
            isEnabled = siteUrlValidatorState is PhotosSiteUrlValidatorState.Success,
            onButtonClick = {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) onEvent(PhotosCompressingEvent.OnLaunchSite)
                    else postNotificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
                else onEvent(PhotosCompressingEvent.OnLaunchSite)
            }
        )
        photosCompressingState.uncompressedPhotoUri?.let {
            PhotoLoader(
                modifier = Modifier.size(dimensionResource(id = R.dimen._350dp)),
                content = it
            )
        }
        if (photosCompressingState.isCompressing) {
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen._25dp)))
            CircularProgressIndicator()
        }
        photosCompressingState.compressedBitmap?.let {
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen._25dp)))
            Image(
                modifier = Modifier.size(dimensionResource(id = R.dimen._350dp)),
                bitmap = it.asImageBitmap(),
                contentDescription = null
            )
        }
    }
}