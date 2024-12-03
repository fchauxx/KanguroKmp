package com.insurtech.kanguro.domain.claimsChatbot

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SingleAction(val label: String, val action: () -> Unit) : Parcelable
