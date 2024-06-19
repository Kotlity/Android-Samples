package com.kotlity.workmanager.repositories.validator

import android.util.Patterns

class IsCorrectSiteUrlFormatValidator {

    operator fun invoke(text: String): Boolean {
        return Patterns.WEB_URL.matcher(text.lowercase()).matches()
    }
}