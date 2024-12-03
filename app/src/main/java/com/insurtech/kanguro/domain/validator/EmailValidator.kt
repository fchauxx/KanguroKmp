package com.insurtech.kanguro.domain.validator

import android.util.Patterns
import com.insurtech.kanguro.R

class EmailValidator : Validator<String> {
    override fun validate(value: String?): Int? {
        return R.string.invalid_email.takeUnless {
            Patterns.EMAIL_ADDRESS.matcher(value.orEmpty()).matches()
        }
    }
}
