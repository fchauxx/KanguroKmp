package com.insurtech.kanguro.networking.dto

import com.squareup.moshi.Json

data class PlaceDto(
    @Json(name = "place_id")
    val placeId: String?,
    val name: String?,
    @Json(name = "formatted_address")
    val formattedAddress: String?,
    val geometry: Geometry?,
    @Json(name = "formatted_phone_number")
    val formattedPhoneNumber: String?,
    @Json(name = "opening_hours")
    val openingHours: PlaceOpeningHours?
)

data class Geometry(
    val location: LatLngLiteral
)

data class LatLngLiteral(
    val lat: Double,
    val lng: Double
)

data class PlaceOpeningHours(
    /**
     * A boolean value indicating if the place is open at the current time.
     */
    @Json(name = "open_now")
    val openNow: Boolean?,
    /**
     * An array of opening periods covering seven days, starting from Sunday, in chronological order.
     */
    val periods: List<PlaceOpeningHoursPeriod>?
)

data class PlaceOpeningHoursPeriod(
    /**
     * Contains a pair of day and time objects describing when the place opens.
     */
    val open: PlaceOpeningHoursPeriodDetail,
    /**
     * May contain a pair of day and time objects describing when the place closes.
     * If a place is always open, the close section will be missing from the response.
     * Clients can rely on always-open being represented as an open period containing day with value 0 and time with value 0000, and no close.
     */
    val close: PlaceOpeningHoursPeriodDetail?
)

data class PlaceOpeningHoursPeriodDetail(
    /**
     * A number from 0–6, corresponding to the days of the week, starting on Sunday.
     * For example, 2 means Tuesday.
     */
    val day: Int,
    /**
     * May contain a time of day in 24-hour HHMM format. Values are in the range 0000–2359.
     * The time will be reported in the place’s time zone.
     */
    val time: String
) {
    val formattedTime: String by lazy {
        StringBuilder(time).apply { insert(2, ":") }.toString()
    }
}
