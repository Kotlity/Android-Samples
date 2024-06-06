package com.kotlity.services.launcher

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri

class SiteLauncher(private val context: Context): Launcher {

    override fun launch(siteUrl: String) {
        val chromeIntent = Intent(Intent.ACTION_VIEW, Uri.parse(siteUrl)).apply {
            setPackage("com.android.chrome")
        }
        try {
            context.startActivity(chromeIntent)
        } catch (ex: ActivityNotFoundException) {
            chromeIntent.setPackage(null)
            context.startActivity(chromeIntent)
        }
    }
}