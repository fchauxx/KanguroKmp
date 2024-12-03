package com.insurtech.kanguro.domain.validator

import androidx.annotation.StringRes

class GenericLengthValidator(
    private val minLength: Int,
    @StringRes private val error: Int
) : Validator<String> {
    override fun validate(value: String?): Int? {
        return error.takeIf { value.orEmpty().length < minLength }
    }
}
