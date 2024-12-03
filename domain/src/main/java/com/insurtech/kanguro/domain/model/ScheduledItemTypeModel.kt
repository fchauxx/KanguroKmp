package com.insurtech.kanguro.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ScheduledItemTypeModel(
    val id: String,
    val label: String
) : Parcelable
