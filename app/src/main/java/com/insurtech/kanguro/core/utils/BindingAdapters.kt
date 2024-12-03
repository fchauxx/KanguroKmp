package com.insurtech.kanguro.core.utils

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.text.Spannable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.insurtech.kanguro.R
import com.insurtech.kanguro.common.date.DateUtils
import com.insurtech.kanguro.domain.coverage.Claim
import com.insurtech.kanguro.domain.model.AmountInfo
import com.insurtech.kanguro.domain.model.Deductible
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

@BindingAdapter("android:enabled")
fun TextView.setViewEnabled(isEnabled: Boolean) {
    this.isEnabled = isEnabled
}

@BindingAdapter("android:activated")
fun TextView.setViewActivated(isActivated: Boolean) {
    this.isActivated = isActivated
}

@BindingAdapter("navigateOnClick")
fun View.navigateOnClick(@IdRes directions: Int) {
    setOnClickListener(Navigation.createNavigateOnClickListener(directions))
}

@BindingAdapter("navigateOnClick")
fun View.navigateOnClick(directions: NavDirections) {
    setOnClickListener(Navigation.createNavigateOnClickListener(directions))
}

@BindingAdapter("android:background")
fun View.setBackgroundRes(@DrawableRes res: Int) {
    setBackgroundResource(res)
}

@BindingAdapter("error")
fun TextInputLayout.setInputError(error: String?) {
    setError(error)
}

@BindingAdapter("onImeAction")
fun TextInputEditText.onImeAction(onAction: () -> Unit) {
    setOnEditorActionListener { _, _, _ ->
        onAction()
        true
    }
}

@BindingAdapter("onFocusChanged")
fun EditText.onFocusChanged(listener: View.OnFocusChangeListener) {
    onFocusChangeListener = listener
}

@BindingAdapter("android:visibility")
fun View.setVisibility(isVisible: Boolean) {
    this.isVisible = isVisible
}

@BindingAdapter("isInvisible")
fun View.setInvisibility(isInvisible: Boolean) {
    this.isInvisible = isInvisible
}

@BindingAdapter("setIsActive")
fun TextView.setActiveText(isActive: Boolean) {
    this.text = if (isActive) {
        context.getString(R.string.active)
    } else {
        context.getString(R.string.inactive)
    }
}

@BindingAdapter("drawableStart")
fun TextView.setDrawableStart(@DrawableRes drawableRes: Int) {
    setCompoundDrawablesWithIntrinsicBounds(drawableRes, 0, 0, 0)
}

@BindingAdapter("drawableEnd")
fun TextView.setDrawableEnd(@DrawableRes drawableRes: Int) {
    setCompoundDrawablesWithIntrinsicBounds(0, 0, drawableRes, 0)
}

@BindingAdapter("claimStatusBackground")
fun TextView.setClaimStatusBackground(claim: Claim) {
    background = ContextCompat.getDrawable(context, claim.getBackgroundColor())
    setTextColor(ContextCompat.getColor(context, claim.getTextColor()))
}

@BindingAdapter("android:src")
fun ImageView.setImageSrc(resource: Int) {
    this.setImageResource(resource)
}

@BindingAdapter("android:src")
fun ImageView.setResourceUrl(url: String) {
    Glide.with(this).load(url).into(this)
}

@BindingAdapter("android:src", "placeholder", requireAll = true)
fun ImageView.setGlideUrlWithPlaceholder(glideUrl: GlideUrl?, placeholder: Int) {
    val notInCacheImage = Glide.with(this)
        .load(glideUrl)
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .skipMemoryCache(true)
        .placeholder(placeholder)
        .error(placeholder)

    Glide.with(this)
        .load(glideUrl)
        .placeholder(placeholder)
        .error(notInCacheImage)
        .into(this)
}

@BindingAdapter("info", "date", "isUTC", requireAll = false)
fun TextView.setClaimDate(info: String?, date: Date, isUTC: Boolean = false) {
    val formattedDate = if (isUTC) {
        DateUtils.getFormattedLocalDate(
            date,
            "MMM dd, yyyy",
            timeZone = TimeZone.getTimeZone("UTC")
        )
    } else {
        DateUtils.getFormattedLocalDate(date, "MMM dd, yyyy")
    }

    text = if (info != null) {
        val dateString = context.getString(R.string.two_strings_template, info, formattedDate)
        val color = ContextCompat.getColor(context, R.color.secondary_dark)

        val span = SpannableString(dateString).apply {
            val indexStart = indexOf(info) + info.length
            setSpan(
                StyleSpan(Typeface.BOLD),
                indexStart,
                dateString.length,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE
            )
            setSpan(
                ForegroundColorSpan(color),
                indexStart,
                dateString.length,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE
            )
        }

        span
    } else {
        formattedDate
    }
}

@BindingAdapter("format", "date", requireAll = true)
fun TextView.setFormattedDate(dateFormat: String, date: Date?) {
    val format = SimpleDateFormat(dateFormat, Locale.getDefault())
    format.timeZone = TimeZone.getTimeZone("UTC")
    text = date?.let { format.format(it) } ?: ""
}

fun convertToCurrency(value: Double): String {
    val numberFormat = NumberFormat.getCurrencyInstance().apply {
        maximumFractionDigits = 2
        currency = Currency.getInstance("USD")
    }

    return numberFormat.format(value)
}

@BindingAdapter("currencyValue", "currencyInfo", requireAll = false)
fun TextView.setCurrencyValue(currencyValue: Number?, currencyInfo: String?) {
    if (currencyValue == null) {
        text = null
        return
    }

    val formattedValue = convertToCurrency(currencyValue.toDouble())

    text = if (currencyInfo == null) {
        formattedValue
    } else {
        context.getString(R.string.two_strings_template, currencyInfo, formattedValue)
    }
}

@BindingAdapter("deductibleValue")
fun TextView.setDeductible(deductible: Deductible) {
    val consumed = convertToCurrency(deductible.consumed?.toDouble() ?: 0.0)
    val limit = convertToCurrency(deductible.limit?.toDouble() ?: 0.0)

    text = setBoldSpannable(
        context.getString(R.string.used_of_limit, consumed, limit),
        consumed
    )
}

@BindingAdapter("annualLimitValue")
fun TextView.setAnnualLimit(annualLimit: AmountInfo) {
    val consumed = convertToCurrency(annualLimit.consumed?.toDouble() ?: 0.0)
    val limit = convertToCurrency(annualLimit.limit?.toDouble() ?: 0.0)

    text = setBoldSpannable(
        context.getString(R.string.used_of_limit, consumed, limit),
        consumed
    )
}

private fun setBoldSpannable(mainText: String, boldText: String): SpannableString {
    return SpannableString(mainText).apply {
        val indexStart = indexOf(boldText)
        setSpan(
            StyleSpan(Typeface.BOLD),
            indexStart,
            indexStart + boldText.length,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )
    }
}

@SuppressLint("SetTextI18n")
@BindingAdapter("reimbursementValue")
fun TextView.setReimbursement(value: Double) {
    text = "${value.toInt()}%"
}

@BindingAdapter("remainingValue")
fun TextView.setRemainingValue(value: Float) {
    val currencyString = convertToCurrency(value.toDouble())
    val color = ContextCompat.getColor(context, R.color.tertiary_dark)
    text = SpannableString(context.getString(R.string.you_have_remaining, currencyString)).apply {
        val indexStart = indexOf(currencyString)
        setSpan(
            StyleSpan(Typeface.BOLD),
            indexStart,
            indexStart + currencyString.length,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )
        setSpan(
            ForegroundColorSpan(color),
            indexStart,
            indexStart + currencyString.length,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )
    }
}

@BindingAdapter("upToReimbursement")
fun TextView.setUpToReimbursement(value: Double) {
    val textToSet = context.getString(R.string.remember_reimbursement, value.toInt())

    val toSpan = SpannableString(textToSet).apply {
        val start = 0
        val end = textToSet.indexOf(" ")
        val color = ContextCompat.getColor(context, R.color.tertiary_darkest)

        setSpan(
            StyleSpan(Typeface.BOLD),
            start,
            end,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )

        setSpan(
            ForegroundColorSpan(color),
            start,
            end,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )
    }

    text = toSpan
}

@BindingAdapter("error")
fun TextInputLayout.setErrorTextRes(@StringRes error: Int?) {
    setError(error?.let { context.getString(it) })
}

@BindingAdapter("renewDate")
fun TextView.setRenewDate(date: Date?) {
    val format = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())

    text = if (date != null) {
        val formattedDate = format.format(date)
        val renewDate = context.getString(R.string.renew_in, formattedDate)
        renewDate
    } else {
        ""
    }
}

@BindingAdapter("android:progress")
fun LinearProgressIndicator.setProgressFloat(progress: Float) {
    setProgressCompat(progress.roundToInt(), true)
}

@BindingAdapter("srcGif", "srcGifPlaceholder", requireAll = false)
fun ImageView.setImageGif(@DrawableRes resource: Int, placeholderRes: Drawable?) {
    var builder = Glide.with(context)
        .load(resource)
        .centerInside()

    if (placeholderRes != null) {
        builder = builder.placeholder(placeholderRes)
    }

    builder.into(GifDrawableImageViewTarget(this, 1))
}

@BindingAdapter("srcEndlessGif", requireAll = false)
fun ImageView.setEndlessImageGif(@DrawableRes resource: Int) {
    val builder = Glide.with(context)
        .load(resource)
        .centerInside()

    builder.into(GifDrawableImageViewTarget(this, GifDrawable.LOOP_FOREVER))
}

@BindingAdapter("icon")
fun MaterialButton.setDrawableIcon(@DrawableRes icon: Int) {
    setIconResource(icon)
}

@BindingAdapter("claimAmount")
fun TextView.setClaimAmount(amount: Double?) {
    val currencyString = convertToCurrency(amount ?: 0.0)
    text = SpannableString(context.getString(R.string.claim_amount_suffix, currencyString)).apply {
        setSpan(StyleSpan(Typeface.BOLD), currencyString)
    }
}

@BindingAdapter("android:setTextColor")
fun TextView.setColor(@ColorRes color: Int) {
    setTextColor(ColorStateList.valueOf(ContextCompat.getColor(context, color)))
}

@BindingAdapter("drawableTint")
fun TextView.setDrawableTint(@ColorRes color: Int) {
    compoundDrawableTintList = ColorStateList.valueOf(ContextCompat.getColor(context, color))
}

@BindingAdapter("android:textWatcher")
fun TextInputEditText.addTextWatcher(watcher: TextWatcher) {
    addTextChangedListener(watcher)
}

@BindingAdapter("android:cardBackgroundColor")
fun MaterialCardView.setCardBackground(@ColorRes color: Int) {
    setCardBackgroundColor(ContextCompat.getColor(context, color))
}

@BindingAdapter("strokeColor")
fun MaterialCardView.setStrokeColor(@ColorRes color: Int) {
    strokeColor = ContextCompat.getColor(context, color)
}

@BindingAdapter("tint")
fun ImageView.setTint(@ColorRes color: Int) {
    drawable.setTint(ContextCompat.getColor(context, color))
}
