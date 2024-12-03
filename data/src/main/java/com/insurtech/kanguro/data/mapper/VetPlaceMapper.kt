package com.insurtech.kanguro.data.mapper

import android.location.Location
import android.location.LocationManager
import com.insurtech.kanguro.common.date.DateUtils
import com.insurtech.kanguro.common.enums.PlaceStatus
import com.insurtech.kanguro.domain.model.VetPlace
import com.insurtech.kanguro.networking.dto.PlaceDto
import com.insurtech.kanguro.networking.dto.PlaceOpeningHours

object VetPlaceMapper {

    private const val MILES_MEASUREMENT = 0.000621371192

    fun mapFromPlaceResponse(placeResponse: PlaceDto, userLocation: Location): VetPlace {
        val lat = placeResponse.geometry?.location?.lat ?: 0.0
        val lng = placeResponse.geometry?.location?.lng ?: 0.0
        return VetPlace(
            id = placeResponse.placeId ?: "",
            name = placeResponse.name ?: "",
            address = placeResponse.formattedAddress ?: "",
            lat = lat,
            lng = lng,
            status = getStatus(placeResponse.openingHours),
            operatingHour = getOperationHour(placeResponse.openingHours),
            phone = placeResponse.formattedPhoneNumber,
            distanceFromUser = getDistanceFromUser(lat, lng, userLocation)
        )
    }

    private fun getDistanceFromUser(lat: Double, lng: Double, userLocation: Location): Double {
        return userLocation.distanceTo(
            Location(LocationManager.GPS_PROVIDER).apply {
                latitude = lat
                longitude = lng
            }
        ) * MILES_MEASUREMENT
    }

    private fun getStatus(openingHours: PlaceOpeningHours?): PlaceStatus? {
        if (openingHours?.openNow == null) {
            return null
        }

        return if (openingHours.openNow == true) {
            PlaceStatus.OPEN
        } else {
            PlaceStatus.CLOSED
        }
    }

    private fun getOperationHour(openingHours: PlaceOpeningHours?): String? {
        val dayOfWeekOfToday: Int = DateUtils.getDayOfWeekOfToday()

        val dayOfWeekOfTomorrow = if (dayOfWeekOfToday == 7) {
            1
        } else {
            dayOfWeekOfToday + 1
        }

        val dayClosesIndex =
            openingHours?.periods?.indexOfFirst { it.close?.day == dayOfWeekOfToday - 1 } ?: -1

        val dayOpensIndex =
            openingHours?.periods?.indexOfFirst { it.open.day == dayOfWeekOfTomorrow - 1 } ?: -1

        return when (getStatus(openingHours)) {
            PlaceStatus.OPEN -> if (dayClosesIndex != -1) {
                openingHours?.periods?.get(dayClosesIndex)?.close?.formattedTime
            } else {
                null
            }

            PlaceStatus.CLOSED -> if (dayOpensIndex != -1) {
                openingHours?.periods?.get(dayOpensIndex)?.open?.formattedTime
            } else {
                openingHours?.periods?.get(0)?.open?.formattedTime
            }

            else -> null
        }
    }
}
