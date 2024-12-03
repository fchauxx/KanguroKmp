package com.insurtech.kanguro.core.utils

import android.content.Context
import com.insurtech.kanguro.domain.validator.Validator

class ValueAndErrorValidation<V>(
    context: Context,
    private vararg var validators: Validator<V>
) : ValueAndError<V>() {

    private val applicationContext = context.applicationContext

    val isValid: Boolean
        get() {
            validators.forEach {
                it.validate(value)?.let { errorId ->
                    error.value = applicationContext.getString(errorId)
                    return false
                }
            }
            error.value = null
            return true
        }

    companion object {
        fun validateAll(vararg fields: ValueAndErrorValidation<*>): Boolean =
            fields.fold(true) { acc, field -> field.isValid && acc }
    }
}
