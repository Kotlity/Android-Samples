package com.kotlity.services.helpers

import com.kotlity.services.R
import com.kotlity.services.helpers.ErrorType.SiteUrlError

fun ErrorType.asUiText(): UiText {
    return when(this) {
        SiteUrlError.SITE_URL_IS_BLANK -> UiText.StringResource(
            R.string.siteTextFieldIsBlankErrorMessage
        )
        SiteUrlError.INCORRECT_SITE_URL_FORMAT -> UiText.StringResource(
            R.string.siteTextFieldIncorrectUrlFormatErrorMessage
        )
    }
}