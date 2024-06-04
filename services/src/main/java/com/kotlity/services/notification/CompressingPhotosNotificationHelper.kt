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
import com.kotlity.services.helpers.Constants.INITIAL_COMPRESSING_PROGRESS_NOTIFICATION
import com.kotlity.services.helpers.Constants.MAX_COMPRESSING_PROGRESS_NOTIFICATION
import com.kotlity.services.helpers.Constants.NOTIFICATION_ID

class CompressingPhotosNotificationHelper(private val context: Context, private val activity: Class<*>): NotificationHelper {

    override var notificationBuilder: NotificationCompat.Builder? = null

    private fun createPendingActivityIntent(progress: Int): PendingIntent {
        val notificationIntent = Intent(context, activity).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP
            putExtra(context.getString(R.string.intentActivityProgressExtraName), progress)
        }
        return PendingIntent.getActivity(context, COMPRESSING_IMAGE_REQUEST_CODE, notificationIntent, PendingIntent.FLAG_IMMUTABLE)
    }

    override fun createOrUpdateNotification(progress: Int): Notification {
        notificationBuilder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(androidx.core.R.drawable.notification_bg)
            .setContentTitle(context.getString(R.string.notificationContentTitleInProgress))
            .setProgress(MAX_COMPRESSING_PROGRESS_NOTIFICATION, progress, false)
            .setOngoing(true)
            .setAutoCancel(false)
            .setContentIntent(createPendingActivityIntent(progress))

        return notificationBuilder!!.build()
    }

    @SuppressLint("MissingPermission")
    override fun deleteNotification() {
        NotificationManagerCompat.from(context).apply {
            notificationBuilder?.let {
                it.apply {
                    setContentTitle(context.getString(R.string.notificationContentTitleCompleted))
                    setProgress(INITIAL_COMPRESSING_PROGRESS_NOTIFICATION, INITIAL_COMPRESSING_PROGRESS_NOTIFICATION, false)
                    setOngoing(false)
                    setAutoCancel(true)
                    setContentIntent(createPendingActivityIntent(INITIAL_COMPRESSING_PROGRESS_NOTIFICATION))
                }
            }
            notify(NOTIFICATION_ID, notificationBuilder!!.build())
        }
        notificationBuilder = null
    }
}