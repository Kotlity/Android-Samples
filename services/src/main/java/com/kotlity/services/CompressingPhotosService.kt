package com.kotlity.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.kotlity.services.compressing_photos.CompressingPhotosHelper
import com.kotlity.services.compressing_photos.ForegroundCompressingPhotosHelper
import com.kotlity.services.file.FileCompressionHelper
import com.kotlity.services.file.PhotosFileCompressionHelper
import com.kotlity.services.helpers.CompressingPhotoServiceActions
import com.kotlity.services.helpers.CompressingPhotosResult
import com.kotlity.services.helpers.Constants.DEFAULT_PHOTO_COMPRESSION_IMAGE_LIMIT
import com.kotlity.services.helpers.Constants.NOTIFICATION_ID
import com.kotlity.services.helpers.Constants.PHOTO_COMPRESSION_ACTION_RESULT
import com.kotlity.services.helpers.Constants.PHOTO_COMPRESSION_IMAGE_LIMIT
import com.kotlity.services.helpers.Constants.PHOTO_COMPRESSION_IMAGE_PATH
import com.kotlity.services.helpers.Constants.PHOTO_COMPRESSION_RESULT
import com.kotlity.services.notification.CompressingPhotosNotificationHelper
import com.kotlity.services.notification.NotificationHelper
import com.kotlity.services.ui.CompressingPhotosActivity
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class CompressingPhotosService: Service() {

    private lateinit var notificationHelper: NotificationHelper
    private lateinit var fileCompressionHelper: FileCompressionHelper
    private lateinit var compressingPhotosHelper: CompressingPhotosHelper
    private val compressingPhotosExceptionHandler = CoroutineExceptionHandler { _, _ ->

    }
    private val compressingPhotosCoroutineContext = Job() + Dispatchers.IO + compressingPhotosExceptionHandler
    private var compressingPhotosCoroutineScope = CoroutineScope(compressingPhotosCoroutineContext)

    override fun onCreate() {
        super.onCreate()
        notificationHelper = CompressingPhotosNotificationHelper(this, CompressingPhotosActivity::class.java)
        fileCompressionHelper = PhotosFileCompressionHelper(this)
        compressingPhotosHelper = ForegroundCompressingPhotosHelper(this, fileCompressionHelper)
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        compressingPhotosCoroutineScope.launch {
            handleServiceActions(intent)
        }
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        compressingPhotosCoroutineScope.cancel()
    }

    private suspend  fun handleServiceActions(intent: Intent) {
        when(intent.action) {
            CompressingPhotoServiceActions.START.toString() -> {
                val stringImagePath = intent.getStringExtra(PHOTO_COMPRESSION_IMAGE_PATH) ?: ""
                val compressionImageLimit = intent.getLongExtra(PHOTO_COMPRESSION_IMAGE_LIMIT, DEFAULT_PHOTO_COMPRESSION_IMAGE_LIMIT)
                startCompressingPhotos(stringImagePath, compressionImageLimit)
            }
            CompressingPhotoServiceActions.STOP.toString() -> stopCompressingPhotos()
        }
    }

    private suspend fun startCompressingPhotos(stringImagePath: String, compressionImageLimit: Long) {
        val notification = notificationHelper.createNotification()
        startForeground(NOTIFICATION_ID, notification)
        val compressingResult = compressingPhotosHelper.compressImage(stringImagePath, compressionImageLimit)
        when(compressingResult) {
            is CompressingPhotosResult.Error -> onCompressingPhotosResult(result = compressingResult.errorMessage)
            is CompressingPhotosResult.Success -> onCompressingPhotosResult(result = compressingResult.compressedPhotoFilePath)
        }
    }

    private fun onCompressingPhotosResult(result: String) {
        val broadcastReceiverIntent = Intent(PHOTO_COMPRESSION_ACTION_RESULT).apply {
            putExtra(PHOTO_COMPRESSION_RESULT, result)
        }
        sendBroadcast(broadcastReceiverIntent)
    }

    private fun stopCompressingPhotos() {
        notificationHelper.showFinishNotification()
        stopForeground(STOP_FOREGROUND_DETACH)
        stopSelf()
    }
}