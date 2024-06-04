package com.kotlity.services.notification

import android.app.Notification
import androidx.core.app.NotificationCompat

interface NotificationHelper {

    val notificationBuilder: NotificationCompat.Builder?
    fun createOrUpdateNotification(progress: Int): Notification
    fun deleteNotification()
}