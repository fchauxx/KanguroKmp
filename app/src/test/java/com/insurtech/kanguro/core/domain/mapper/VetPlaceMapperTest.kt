package com.insurtech.kanguro.core.domain.mapper

import android.location.Location
import com.insurtech.kanguro.common.date.DateUtils
import com.insurtech.kanguro.common.enums.PlaceStatus
import com.insurtech.kanguro.data.mapper.VetPlaceMapper
import com.insurtech.kanguro.networking.dto.Geometry
import com.insurtech.kanguro.networking.dto.LatLngLiteral
import com.insurtech.kanguro.networking.dto.PlaceDto
import com.insurtech.kanguro.networking.dto.PlaceOpeningHours
import com.insurtech.kanguro.networking.dto.PlaceOpeningHoursPeriod
import com.insurtech.kanguro.networking.dto.PlaceOpeningHoursPeriodDetail
import io.mockk.every
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import io.mockk.mockkObject
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
class VetPlaceMapperTest {

    private val userLocation: Location = mockk()

    private val milesMeasurement = 0.000621371192

    @get:Rule
    val mockkRule = MockKRule(this)

    @Before
    fun setup() {
        mockkObject(DateUtils)

        every {
            userLocation.distanceTo(any())
        } returns 100.0F
    }

    @Test
    fun `Map Closed PlaceResponse to Closed VetPlace`() {
        // Arrange
        val lat = 30.015173043384674
        val lng = -51.18642318991744

        val geometryResponse = Geometry(LatLngLiteral(lat = lat, lng = lng))

        val placeResponse = PlaceDto(
            placeId = "123",
            name = "Place Name",
            formattedAddress = "R. Dom Pedro II, 978 - Higienópolis, Porto Alegre",
            geometry = geometryResponse,
            formattedPhoneNumber = "+55 51 99445-2112",
            openingHours = getFakePlaceOpeningHours(false)
        )

        every {
            DateUtils.getDayOfWeekOfToday()
        } returns 1 // Sunday

        // Act
        val mappedVetPlace = VetPlaceMapper.mapFromPlaceResponse(placeResponse, userLocation)

        // Assert
        assertEquals(placeResponse.placeId, mappedVetPlace.id)
        assertEquals(placeResponse.name, mappedVetPlace.name)
        assertEquals(placeResponse.formattedAddress, mappedVetPlace.address)
        assertEquals(lat, mappedVetPlace.lat)
        assertEquals(lng, mappedVetPlace.lng)
        assertEquals(PlaceStatus.CLOSED, mappedVetPlace.status)
        assertEquals("10:00", mappedVetPlace.operatingHour)
        assertEquals(100.0F * milesMeasurement, mappedVetPlace.distanceFromUser)
    }

    @Test
    fun `Map Open PlaceResponse to Open VetPlace`() {
        // Arrange
        val lat = 30.015173043384674
        val lng = -51.18642318991744

        val geometryResponse = Geometry(LatLngLiteral(lat = lat, lng = lng))

        val placeResponse = PlaceDto(
            placeId = "123",
            name = "Place Name",
            formattedAddress = "R. Dom Pedro II, 978 - Higienópolis, Porto Alegre",
            geometry = geometryResponse,
            formattedPhoneNumber = "+55 51 99445-2112",
            openingHours = getFakePlaceOpeningHours(true)
        )

        every {
            DateUtils.getDayOfWeekOfToday()
        } returns 2 // Monday

        // Act
        val mappedVetPlace = VetPlaceMapper.mapFromPlaceResponse(placeResponse, userLocation)

        // Assert
        assertEquals(placeResponse.placeId, mappedVetPlace.id)
        assertEquals(placeResponse.name, mappedVetPlace.name)
        assertEquals(placeResponse.formattedAddress, mappedVetPlace.address)
        assertEquals(lat, mappedVetPlace.lat)
        assertEquals(lng, mappedVetPlace.lng)
        assertEquals(placeResponse.formattedPhoneNumber, mappedVetPlace.phone)
        assertEquals("18:00", mappedVetPlace.operatingHour)
        assertEquals(100.0F * milesMeasurement, mappedVetPlace.distanceFromUser)
    }

    private fun getFakePlaceOpeningHours(isOpen: Boolean): PlaceOpeningHours {
        val openDetail1 = PlaceOpeningHoursPeriodDetail(
            day = 0, // Sunday
            time = "2359"
        )

        val openDetail2 = PlaceOpeningHoursPeriodDetail(
            day = 1, // Monday
            time = "1000"
        )

        val closeDetail2 = PlaceOpeningHoursPeriodDetail(
            day = 1, // Monday
            time = "1800"
        )

        val placeOpeningHoursPeriod1 = PlaceOpeningHoursPeriod(
            open = openDetail1,
            close = null
        )

        val placeOpeningHoursPeriod2 = PlaceOpeningHoursPeriod(
            open = openDetail2,
            close = closeDetail2
        )

        return PlaceOpeningHours(
            openNow = isOpen,
            periods = listOf(placeOpeningHoursPeriod1, placeOpeningHoursPeriod2)
        )
    }
}
