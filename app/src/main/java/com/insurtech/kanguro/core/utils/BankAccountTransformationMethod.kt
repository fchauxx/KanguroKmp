package com.insurtech.kanguro.core.utils

import android.graphics.Rect
import android.text.method.TransformationMethod
import android.view.View
import android.widget.EditText

class BankAccountTransformationMethod : TransformationMethod {

    private var isFocused = false

    override fun getTransformation(source: CharSequence, view: View): CharSequence {
        if (isFocused) {
            return source
        }

        return MaskedCharSequence(source)
    }

    override fun onFocusChanged(
        view: View?,
        sourceText: CharSequence?,
        focused: Boolean,
        direction: Int,
        previouslyFocusedRect: Rect?
    ) {
        isFocused = focused
        (view as? EditText)?.setText(sourceText)
    }
}
