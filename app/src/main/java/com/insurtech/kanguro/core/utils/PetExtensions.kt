package com.insurtech.kanguro.core.utils

import android.content.Context
import com.insurtech.kanguro.R
import com.insurtech.kanguro.common.enums.PetType
import com.insurtech.kanguro.domain.model.Pet
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit

fun Pet.getPlaceholderImage() = when (type) {
    PetType.Cat -> R.drawable.placeholder_cat_picture
    else -> R.drawable.placeholder_dog_picture
}

fun Pet.getAge(unit: ChronoUnit): Int {
    val start: LocalDate = birthDate?.toInstant()?.atZone(ZoneId.systemDefault())?.toLocalDate() ?: LocalDate.now()
    val end: LocalDate = LocalDate.now()
    return unit.between(start, end).toInt()
}

fun Pet.getFormattedBreedAndAge(context: Context): String {
    var age = getAge(ChronoUnit.YEARS)

    val ageString = if (age == 0) {
        age = getAge(ChronoUnit.MONTHS)
        context.resources.getQuantityString(R.plurals.plural_month, age, age)
    } else {
        context.resources.getQuantityString(R.plurals.plural_year, age, age)
    }

    return if (breed.isNullOrEmpty()) {
        ageString
    } else {
        "$breed, $ageString"
    }
}

fun Pet.getCacheableImage(): GlideUrlWithParameters? {
    return this.petPictureUrl.takeUnless { petPictureUrl ->
        petPictureUrl.isNullOrEmpty()
    }?.let {
        GlideUrlWithParameters(it)
    }
}
