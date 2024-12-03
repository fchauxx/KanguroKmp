package com.insurtech.kanguro.designsystem.ui.views

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.StyleRes
import androidx.core.content.withStyledAttributes
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.core.widget.TextViewCompat
import androidx.core.widget.addTextChangedListener
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout
import com.insurtech.kanguro.designsystem.R

class KanguroTextInputLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.kanguroInputLayoutStyleAttr
) : TextInputLayout(context, attrs, defStyleAttr) {

    @StyleRes
    private var titleTextAppearance: Int = R.style.KanguroTextStyle_Caption_Regular
    private var titleColor: ColorStateList? = null
    private var titleColorFilled: ColorStateList? = null

    private var originalEditTextBackground: Drawable? = null
    private var errorBackgroundDrawable: Drawable? = null

    private val titleTextView: TextView = TextView(context)

    var title: String? = null
        set(value) {
            field = value
            titleTextView.isVisible = !value.isNullOrEmpty()
            titleTextView.text = value
            editText?.let { updateTitleColor(it) }
        }

    init {
        context.withStyledAttributes(attrs, R.styleable.KanguroTextInputLayout, defStyleAttr) {
            titleTextAppearance = getResourceId(
                R.styleable.KanguroTextInputLayout_kg_titleTextAppearance,
                R.style.KanguroTextStyle_Caption_Regular
            )
            titleColor = getColorStateList(
                R.styleable.KanguroTextInputLayout_kg_titleColor
            )
            titleColorFilled = getColorStateList(
                R.styleable.KanguroTextInputLayout_kg_titleColorFilled
            )
            title = getString(R.styleable.KanguroTextInputLayout_kg_title)
            errorBackgroundDrawable = getDrawable(R.styleable.KanguroTextInputLayout_kg_errorBackground)
        }
        initialize()

        // isEnabled on TextInputLayout is recursive, so we must re-trigger after adding the views.
        isEnabled = isEnabled
    }

    private fun initialize() {
        titleTextView.setPadding(0, 0, 0, resources.getDimensionPixelSize(R.dimen.spacing_nano))
        titleTextView.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        TextViewCompat.setTextAppearance(titleTextView, titleTextAppearance)
        addView(titleTextView, 0)
    }

    override fun setError(error: CharSequence?) {
        super.setError(error)
        removePadding()
        updateEditTextBackground()
    }

    private fun updateEditTextBackground() {
        if (!error.isNullOrEmpty() && errorBackgroundDrawable != null) {
            editText?.background = errorBackgroundDrawable
        } else {
            editText?.background = originalEditTextBackground
        }
    }

    private fun updateTitleColor(editText: EditText) {
        if (isEnabled && editText.text?.isNotEmpty() == true) {
            titleColorFilled
        } else {
            titleColor
        }?.let {
            titleTextView.setTextColor(it)
        }
    }

    /**
     * Removes padding from the error textview.
     * This is the only way to do this "cleanly", as we don't have access to the error textview.
     */
    private fun removePadding() {
        for (i in 0 until this.childCount) {
            this.getChildAt(i).updatePadding(left = 0)
        }
    }

    override fun drawableStateChanged() {
        super.drawableStateChanged()
        updateEditTextBackground()
    }

    // Used to grab the original background
    override fun addView(child: View, index: Int, params: ViewGroup.LayoutParams) {
        if (child is EditText && child.background != null) {
            originalEditTextBackground = child.background
            updateTitleColor(child)
            child.addTextChangedListener { updateTitleColor(child) }
        }
        super.addView(child, index, params)
    }
}

@BindingAdapter("kg_title")
fun KanguroTextInputLayout.setTitle(text: String) {
    title = text
}
