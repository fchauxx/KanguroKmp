package com.insurtech.kanguro.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AmountInfo(
    val consumed: Float? = null,
    val limit: Float? = null,
    val remainingValue: Float? = null,
    val id: Long? = null
) : Parcelable
