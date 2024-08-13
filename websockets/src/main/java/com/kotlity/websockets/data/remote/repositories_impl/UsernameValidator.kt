package com.kotlity.websockets.data.remote.repositories_impl

import com.kotlity.websockets.domain.remote.repositories.Validator
import com.kotlity.websockets.utils.responses.HandleStatus
import com.kotlity.websockets.utils.responses.UsernameValidationFailure
import com.kotlity.websockets.utils.validation.IsBlankValidator
import com.kotlity.websockets.utils.validation.IsContainsDigits
import com.kotlity.websockets.utils.validation.IsContainsProhibitedCharacters
import javax.inject.Inject

class UsernameValidator @Inject constructor(
    private val isBlankValidator: IsBlankValidator,
    private val isContainsDigits: IsContainsDigits,
    private val isContainsProhibitedCharacters: IsContainsProhibitedCharacters
): Validator<Unit, UsernameValidationFailure> {

    override fun validate(input: String): HandleStatus<Unit, UsernameValidationFailure> {
        return if (isBlankValidator(input)) HandleStatus.Error(error = UsernameValidationFailure.IS_BLANK)
        else if (isContainsDigits(input)) HandleStatus.Error(error = UsernameValidationFailure.CONTAINS_DIGITS)
        else if (isContainsProhibitedCharacters(input)) HandleStatus.Error(error = UsernameValidationFailure.CONTAINS_PROHIBITED_CHARACTERS)
        else HandleStatus.Success(data = Unit)
    }
}