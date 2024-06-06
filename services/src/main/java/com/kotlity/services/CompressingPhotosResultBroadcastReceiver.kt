package com.kotlity.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.kotlity.services.helpers.Constants.PHOTO_COMPRESSION_ACTION_RESULT
import com.kotlity.services.helpers.Constants.PHOTO_COMPRESSION_RESULT

class CompressingPhotosResultBroadcastReceiver(private val onCompressingPhotosResultReceived: (String) -> Unit): BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == PHOTO_COMPRESSION_ACTION_RESULT) {
            val result = intent.getStringExtra(PHOTO_COMPRESSION_RESULT)
            result?.let {
                onCompressingPhotosResultReceived(it)
            }
        }
    }
}