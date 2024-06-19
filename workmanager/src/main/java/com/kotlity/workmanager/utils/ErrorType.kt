package com.kotlity.workmanager.utils

sealed interface ErrorType {
    enum class SiteUrlError: ErrorType {
        SITE_URL_IS_BLANK,
        INCORRECT_SITE_URL_FORMAT
    }
}