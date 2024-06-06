package com.kotlity.services

import android.annotation.SuppressLint
import android.app.Application
import android.app.NotificationManager
import android.content.Context
import android.widget.Toast
import com.kotlity.services.notification.CompressingPhotosNotificationChannelHelper
import com.kotlity.services.notification.NotificationChannelHelper

class CompressingPhotosApplication: Application() {

    private lateinit var notificationChannelHelper: NotificationChannelHelper
    private lateinit var notificationManager: NotificationManager

    @SuppressLint("NewApi")
    override fun onCreate() {
        super.onCreate()
        notificationChannelHelper = CompressingPhotosNotificationChannelHelper()
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (notificationChannelHelper.notificationChannel == null) Toast.makeText(this, getString(R.string.deviceIsNotSupportingSendingNotifications), Toast.LENGTH_SHORT).show()
        else notificationManager.createNotificationChannel(notificationChannelHelper.notificationChannel!!)
    }
}