package com.insurtech.kanguro.common.enums

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import com.insurtech.kanguro.common.R

enum class PlaceStatus(@StringRes val value: Int, @ColorRes val color: Int) {
    OPEN(R.string.place_open, R.color.place_status_open),
    CLOSED(R.string.place_closed, R.color.place_status_closed)
}
