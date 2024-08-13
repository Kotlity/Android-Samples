package com.kotlity.workmanager.utils

import com.kotlity.workmanager.R
import com.kotlity.workmanager.utils.ErrorType.SiteUrlError.INCORRECT_SITE_URL_FORMAT
import com.kotlity.workmanager.utils.ErrorType.SiteUrlError.SITE_URL_IS_BLANK

fun ErrorType.asUiText(): UiText {
    return when(this) {
        SITE_URL_IS_BLANK -> UiText.StringResource(
            R.string.siteTextFieldIsBlankErrorMessage
        )
        INCORRECT_SITE_URL_FORMAT -> UiText.StringResource(
            R.string.siteTextFieldIncorrectUrlFormatErrorMessage
        )
    }
}