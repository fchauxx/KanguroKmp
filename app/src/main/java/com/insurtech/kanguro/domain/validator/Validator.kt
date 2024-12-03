package com.insurtech.kanguro.domain.validator

import androidx.annotation.StringRes

interface Validator<T> {
    /**
     * Returns an error string Id or null if the value is valid.
     */
    @StringRes fun validate(value: T?): Int?
}
