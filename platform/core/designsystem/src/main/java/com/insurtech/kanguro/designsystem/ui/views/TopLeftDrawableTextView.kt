package com.insurtech.kanguro.designsystem.ui.views

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.insurtech.kanguro.designsystem.R

class TopLeftDrawableTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : AppCompatTextView(context, attrs) {

    private val leftDrawable = ContextCompat.getDrawable(context, R.drawable.img_text_blob)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setBulletPoint()
    }

    private fun setBulletPoint() {
        if (!TextUtils.isEmpty(text)) {
            leftDrawable?.let { drlft ->
//                if (lineCount == 1) {
//                    setCompoundDrawablesWithIntrinsicBounds(drlft, null, null, null)
//                } else {
                val gravityDrawable = TopLeftGravityDrawable(drlft)
                drlft.setBounds(0, 0, drlft.intrinsicWidth, drlft.intrinsicHeight)
                gravityDrawable.setBounds(0, 0, drlft.intrinsicWidth, drlft.intrinsicHeight)
                setCompoundDrawables(gravityDrawable, null, null, null)
//                }
            }
        }
    }
}
