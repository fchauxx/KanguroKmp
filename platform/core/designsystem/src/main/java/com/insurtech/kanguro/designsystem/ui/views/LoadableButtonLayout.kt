package com.insurtech.kanguro.designsystem.ui.views

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.isVisible
import androidx.core.widget.TextViewCompat
import androidx.databinding.BindingAdapter

/**
 * This component should be used with a button and a progress bar as children.
 * It will switch their appearances based on the loading flag.
 */
class LoadableButtonLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var progressBar: ProgressBar? = null
    private var button: AppCompatButton? = null

    private var originalTextColor: ColorStateList? = null
    private var originalDrawableColor: ColorStateList? = null

    var isLoading: Boolean = false
        set(value) {
            if (value != field) {
                field = value
                updateComponentsVisibility()
                updateButtonAppearance()
            }
        }

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        super.addView(child, index, params)
        if (child is AppCompatButton) {
            button = child
            updateComponentsVisibility()
        } else if (child is ProgressBar) {
            progressBar = child
            updateComponentsVisibility()
        }
    }

    private fun updateComponentsVisibility() {
        button?.isClickable = !isLoading
        progressBar?.isVisible = isLoading
    }

    // This code cant run twice without changing state, or the original state will be overwritten
    private fun updateButtonAppearance() {
        if (isLoading) {
            originalTextColor = button?.textColors
            originalDrawableColor = button?.compoundDrawableTintList
            button?.let {
                it.setTextColor(ColorStateList.valueOf(Color.TRANSPARENT))
                TextViewCompat.setCompoundDrawableTintList(
                    it,
                    ColorStateList.valueOf(Color.TRANSPARENT)
                )
            }
        } else {
            button?.let {
                it.setTextColor(originalTextColor ?: return)
                TextViewCompat.setCompoundDrawableTintList(it, originalDrawableColor)
            }
        }
    }
}

@BindingAdapter("isLoading")
fun LoadableButtonLayout.updateLoading(isLoading: Boolean) {
    this.isLoading = isLoading
}
