package com.kotlity.workmanager.repositories.validator

import com.kotlity.workmanager.utils.ErrorType
import com.kotlity.workmanager.ui.states.ValidatorState
import javax.inject.Inject

class SiteUrlValidator @Inject constructor(
    private val isTextBlankValidator: IsTextBlankValidator,
    private val isCorrectSiteUrlFormatValidator: IsCorrectSiteUrlFormatValidator
): TextValidator {

    override fun validateText(text: String): ValidatorState {
        if (isTextBlankValidator(text)) return ValidatorState.Error(errorType = ErrorType.SiteUrlError.SITE_URL_IS_BLANK)
        if (!isCorrectSiteUrlFormatValidator(text)) return ValidatorState.Error(errorType = ErrorType.SiteUrlError.INCORRECT_SITE_URL_FORMAT)
        return ValidatorState.Success
    }
}