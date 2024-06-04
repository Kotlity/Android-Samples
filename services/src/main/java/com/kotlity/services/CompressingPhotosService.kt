package com.kotlity.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.kotlity.services.compressing_photos.CompressingPhotosHelper
import com.kotlity.services.compressing_photos.ForegroundCompressingPhotosHelper
import com.kotlity.services.file.FileCompressionHelper
import com.kotlity.services.file.PhotosFileCompressionHelper
import com.kotlity.services.helpers.CompressingPhotoServiceActions
import com.kotlity.services.notification.CompressingPhotosNotificationHelper
import com.kotlity.services.notification.NotificationHelper

class CompressingPhotosService: Service() {

    private lateinit var notificationHelper: NotificationHelper
    private lateinit var fileCompressionHelper: FileCompressionHelper
    private lateinit var compressingPhotosHelper: CompressingPhotosHelper
    private var compressingImageProgress = 0

    override fun onCreate() {
        super.onCreate()
        notificationHelper = CompressingPhotosNotificationHelper(this, CompressingPhotosActivity::class.java)
        fileCompressionHelper = PhotosFileCompressionHelper(this)
        compressingPhotosHelper = ForegroundCompressingPhotosHelper(this, fileCompressionHelper)
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun handleServiceActions(intent: Intent) {
        when(intent.action) {
            CompressingPhotoServiceActions.START.toString() -> start()
            CompressingPhotoServiceActions.STOP.toString() -> stopSelf()
        }
    }

    private fun start() {

    }
}