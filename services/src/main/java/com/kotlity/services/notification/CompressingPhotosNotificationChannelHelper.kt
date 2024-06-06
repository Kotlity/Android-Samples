package com.kotlity.services.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.kotlity.services.helpers.Constants.NOTIFICATION_CHANNEL_ID
import com.kotlity.services.helpers.Constants.NOTIFICATION_CHANNEL_NAME

class CompressingPhotosNotificationChannelHelper: NotificationChannelHelper {

    override val notificationChannel: NotificationChannel? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_LOW)
    } else null
}