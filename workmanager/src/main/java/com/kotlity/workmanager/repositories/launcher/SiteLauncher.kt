package com.kotlity.workmanager.repositories.launcher

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri

class SiteLauncher(private val context: Context): Launcher {

    override fun launch(siteUrl: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(siteUrl)).apply {
            setPackage("com.android.chrome")
        }
        try {
            context.startActivity(intent)
        } catch (ex: ActivityNotFoundException) {
            intent.setPackage(null)
            context.startActivity(intent)
        }
    }
}