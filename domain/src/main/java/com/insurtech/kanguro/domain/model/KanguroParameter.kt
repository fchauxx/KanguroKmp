package com.insurtech.kanguro.domain.model

import android.os.Parcelable
import com.insurtech.kanguro.common.enums.KanguroParameterType
import kotlinx.parcelize.Parcelize

@Parcelize
data class KanguroParameter(
    val key: Int? = null,
    val value: String? = null,
    val description: String? = null,
    val type: KanguroParameterType? = null,
    val language: String? = null,
    val isActive: Boolean? = null
) : Parcelable
