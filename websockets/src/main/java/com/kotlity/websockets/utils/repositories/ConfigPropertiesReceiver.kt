package com.kotlity.websockets.utils.repositories

import android.content.Context
import java.util.Properties
import javax.inject.Inject

class ConfigPropertiesReceiver @Inject constructor(
    private val appContext: Context
): PropertiesReceiver {

    override fun receiveProperty(propertyKey: String): String? {
        return try {
            val properties = Properties()
            val assetManager = appContext.assets
            assetManager.open("config.properties").use { inputStream ->
                with(properties) {
                    load(inputStream)
                    getProperty(propertyKey)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}