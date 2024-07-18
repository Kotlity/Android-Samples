package com.kotlity.websockets.utils

import android.os.Build
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun Long.formatTime(formatPattern: String): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val instant = Instant.ofEpochMilli(this)
        val dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
        val dateTimeFormatter = DateTimeFormatter.ofPattern(formatPattern)
        dateTime.format(dateTimeFormatter)
    } else {
        val date = Date(this)
        val simpleDateFormat = SimpleDateFormat(formatPattern, Locale.getDefault())
        val defaultTimeZone = TimeZone.getDefault()
        with(simpleDateFormat) {
            timeZone = defaultTimeZone
            format(date)
        }
    }
}