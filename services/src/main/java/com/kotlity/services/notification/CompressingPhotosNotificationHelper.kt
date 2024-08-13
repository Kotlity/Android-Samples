package com.kotlity.services.notification

import android.annotation.SuppressLint
import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.kotlity.services.R
import com.kotlity.services.helpers.Constants.NOTIFICATION_CHANNEL_ID
import com.kotlity.services.helpers.Constants.COMPRESSING_IMAGE_REQUEST_CODE
import com.kotlity.services.helpers.Constants.NOTIFICATION_ID

class CompressingPhotosNotificationHelper(private val context: Context, private val activity: Class<*>): NotificationHelper {

    override var notificationBuilder: NotificationCompat.Builder? = null

    private fun createPendingActivityIntent(): PendingIntent {
        val notificationIntent = Intent(context, activity)
        return PendingIntent.getActivity(context, COMPRESSING_IMAGE_REQUEST_CODE, notificationIntent, PendingIntent.FLAG_IMMUTABLE)
    }

    override fun createNotification(): Notification {
        notificationBuilder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(androidx.core.R.drawable.notification_bg)
            .setContentTitle(context.getString(R.string.notificationContentTitleInProgress))
            .setOngoing(true)
            .setAutoCancel(false)
            .setContentIntent(createPendingActivityIntent())

        return notificationBuilder!!.build()
    }

    @SuppressLint("MissingPermission")
    override fun showNotification(notification: Notification) {
        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, notification)
    }

    @SuppressLint("MissingPermission")
    override fun showFinishNotification() {
        showNotification(
            notificationBuilder!!.apply {
                setContentTitle(context.getString(R.string.notificationContentTitleCompleted))
                setOngoing(false)
                setAutoCancel(true)
            }
                .build()
        )
        notificationBuilder = null
    }
}