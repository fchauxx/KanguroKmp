package com.insurtech.kanguro.ui.scenes.forgotPassword

import android.content.Context
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View
import androidx.fragment.app.Fragment
import com.insurtech.kanguro.R
import com.insurtech.kanguro.core.utils.IntentUtils

class CustomClickableSpan(private val context: Context, private val fragment: Fragment) :
    ClickableSpan() {

    override fun onClick(textView: View) {
        IntentUtils.openMailToKanguro(context)
    }

    override fun updateDrawState(ds: TextPaint) {
        super.updateDrawState(ds)
        val test = R.color.secondary_darkest
        ds.color = fragment.resources.getColor(test)
        ds.isUnderlineText = true
        ds.isFakeBoldText = true
    }
}
