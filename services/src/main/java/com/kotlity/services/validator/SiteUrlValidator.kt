package com.kotlity.services.validator

import com.kotlity.services.helpers.ErrorType
import com.kotlity.services.helpers.ValidatorState

class SiteUrlValidator(
    private val isTextBlankValidator: IsTextBlankValidator,
    private val isCorrectSiteUrlFormatValidator: IsCorrectSiteUrlFormatValidator
): TextValidator {

    override fun validateText(text: String): ValidatorState {
        if (isTextBlankValidator(text)) return ValidatorState.Error(errorType = ErrorType.SiteUrlError.SITE_URL_IS_BLANK)
        if (!isCorrectSiteUrlFormatValidator(text)) return ValidatorState.Error(errorType = ErrorType.SiteUrlError.INCORRECT_SITE_URL_FORMAT)
        return ValidatorState.Success
    }
}