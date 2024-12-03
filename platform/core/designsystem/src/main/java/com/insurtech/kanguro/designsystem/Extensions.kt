package com.insurtech.kanguro.designsystem

import android.content.res.Resources
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.ui.composables.rentersScheduledItemsCategory.domain.ScheduledItemsCategoryType
import com.insurtech.kanguro.designsystem.ui.theme.NegativeMedium
import com.insurtech.kanguro.designsystem.ui.theme.NeutralBackground
import com.insurtech.kanguro.designsystem.ui.theme.NeutralLightest
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryMedium
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Currency
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import kotlin.math.roundToInt

val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).roundToInt()

val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).roundToInt()

fun BigDecimal.toPriceWithDecimalFormat(): String {
    val numberFormat = NumberFormat.getCurrencyInstance().apply {
        currency = Currency.getInstance("USD")
    }

    return numberFormat.format(this)
}

fun Modifier.startEndPaddingScreen(): Modifier =
    this.padding(
        start = 32.dp,
        end = 32.dp
    )

fun Modifier.horizontalPayToVetPaddingScreen(): Modifier =
    this.padding(horizontal = 31.dp)

fun Modifier.outlinedField(): Modifier =
    this
        .border(
            width = 1.dp,
            color = SecondaryMedium,
            shape = RoundedCornerShape(size = 8.dp)
        )
        .padding(16.dp)

fun Modifier.outlinedFieldDisabled(): Modifier =
    this
        .border(
            width = 1.dp,
            color = NeutralLightest,
            shape = RoundedCornerShape(size = 8.dp)
        )
        .background(color = NeutralBackground, shape = RoundedCornerShape(8.dp))
        .padding(16.dp)

fun Modifier.outlinedFieldNegativeMedium(): Modifier =
    this
        .border(
            width = 1.dp,
            color = NegativeMedium,
            shape = RoundedCornerShape(size = 8.dp)
        )
        .padding(16.dp)

fun Long.toStringDate(
    pattern: String = "MM/dd/yyyy"
): String {
    val date = Date(this)
    val formatter = SimpleDateFormat(pattern, Locale.getDefault()).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }
    return formatter.format(date)
}

fun String.toUTC(): String {
    val inputFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    val date = inputFormat.parse(this)

    val outputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    outputFormat.timeZone = TimeZone.getTimeZone("UTC")

    return date?.let { outputFormat.format(it) } ?: "invalid string"
}

fun String.getScheduledItemsCategoryType(): ScheduledItemsCategoryType {
    return when (this) {
        ScheduledItemsCategoryType.Jewelry.toString() -> ScheduledItemsCategoryType.Jewelry
        ScheduledItemsCategoryType.FineArtsAndCollectibles.toString() -> ScheduledItemsCategoryType.FineArtsAndCollectibles
        ScheduledItemsCategoryType.Electronics.toString() -> ScheduledItemsCategoryType.Electronics
        ScheduledItemsCategoryType.MusicalInstruments.toString() -> ScheduledItemsCategoryType.MusicalInstruments
        ScheduledItemsCategoryType.DesignerClothingAndAccessories.toString() -> ScheduledItemsCategoryType.DesignerClothingAndAccessories
        ScheduledItemsCategoryType.SportsEquipment.toString() -> ScheduledItemsCategoryType.SportsEquipment
        ScheduledItemsCategoryType.HighValueAppliances.toString() -> ScheduledItemsCategoryType.HighValueAppliances
        ScheduledItemsCategoryType.RareBooksAndManuscripts.toString() -> ScheduledItemsCategoryType.RareBooksAndManuscripts
        ScheduledItemsCategoryType.HomeOfficeAndHomeVideoEquipment.toString() -> ScheduledItemsCategoryType.HomeOfficeAndHomeVideoEquipment
        ScheduledItemsCategoryType.ElectricScooter.toString() -> ScheduledItemsCategoryType.ElectricScooter
        else -> ScheduledItemsCategoryType.Others
    }
}

fun String.toDollarFormat(): String {
    val minStringLength = 3

    val defaultInitialCharacter = '0'

    val initialIndex = 0

    val decimalSize = 2

    val patternFormat = "#,###"

    val validValue = this.filter { it.isDigit() }.trimStart(defaultInitialCharacter)
        .padStart(minStringLength, defaultInitialCharacter)

    val numberPart = validValue.substring(initialIndex, validValue.length - decimalSize)
        .ifEmpty { defaultInitialCharacter.toString() }

    val decimalPart = validValue.takeLast(decimalSize)

    val formatter = DecimalFormat(patternFormat, DecimalFormatSymbols(Locale.ENGLISH))

    return "${formatter.format(numberPart.toLong())}.$decimalPart"
}

fun Number.toDollarFormat(): String {
    val patternFormat = "#,##0.00"

    return DecimalFormat(patternFormat, DecimalFormatSymbols(Locale.ENGLISH))
        .format(this)
}

fun Number.toDollarFormatNoDecimal(): String {
    val patternFormat = "#,##0"

    return DecimalFormat(patternFormat, DecimalFormatSymbols(Locale.ENGLISH))
        .format(this)
}

fun Date.formatToUS(pattern: String = "MM/dd/yyyy", locale: Locale = Locale.getDefault()): String {
    val dateFormatter = SimpleDateFormat(pattern, locale)
    return dateFormatter.format(this)
}

fun String.getStartIndexOfWord(word: String): Int {
    val modifiedPhrase = " $this"
    val modifiedWord = " $word"

    val index = modifiedPhrase.indexOf(modifiedWord)

    return if (index >= 1) index - 1 else -1
}

fun String.removeStartingNumbersAndPunctuation(): String {
    return this.replaceFirst(Regex("^[0-9.\\s\\W]+"), "")
}
