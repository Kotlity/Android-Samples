package com.kotlity.websockets.utils.repositories

interface PropertiesReceiver {

    fun receiveProperty(propertyKey: String): String?
}