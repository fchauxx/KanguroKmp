package com.insurtech.kanguro.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Deductible(
    val id: Long? = null,
    val limit: Float? = null,
    val consumed: Float? = null
) : Parcelable
