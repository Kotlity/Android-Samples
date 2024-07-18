package com.kotlity.websockets.di.modules

import com.kotlity.websockets.data.remote.repositories_impl.UsernameValidator
import com.kotlity.websockets.di.qualifiers.UsernameValidatorQualifier
import com.kotlity.websockets.domain.remote.repositories.Validator
import com.kotlity.websockets.utils.responses.UsernameValidationFailure
import com.kotlity.websockets.utils.validation.IsBlankValidator
import com.kotlity.websockets.utils.validation.IsContainsDigits
import com.kotlity.websockets.utils.validation.IsContainsProhibitedCharacters
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ValidatorModule {

    @Provides
    @ViewModelScoped
    @UsernameValidatorQualifier
    fun provideUsernameValidator(
        isBlankValidator: IsBlankValidator,
        isContainsDigits: IsContainsDigits,
        isContainsProhibitedCharacters: IsContainsProhibitedCharacters
    ): Validator<Unit, UsernameValidationFailure> = UsernameValidator(
        isBlankValidator = isBlankValidator,
        isContainsDigits = isContainsDigits,
        isContainsProhibitedCharacters = isContainsProhibitedCharacters
    )
}