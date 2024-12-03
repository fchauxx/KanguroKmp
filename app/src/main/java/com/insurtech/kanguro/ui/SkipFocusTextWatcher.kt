package com.insurtech.kanguro.ui

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

class SkipFocusTextWatcher(
    val maxLength: Int,
    val nextField: EditText
) : TextWatcher {

    override fun afterTextChanged(p0: Editable?) {
        if (p0.toString().length >= maxLength) nextField.requestFocus()
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
}
