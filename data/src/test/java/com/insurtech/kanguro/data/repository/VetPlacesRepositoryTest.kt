package com.insurtech.kanguro.data.repository

import android.location.Location
import app.cash.turbine.test
import com.insurtech.kanguro.common.date.DateUtils
import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.data.mapper.NearbyVetPlaceSearchMapper
import com.insurtech.kanguro.data.mapper.VetPlaceMapper
import com.insurtech.kanguro.data.remote.fakes.FakeVetPlacesDataSource
import com.insurtech.kanguro.data.repository.impl.VetPlacesRepository
import com.insurtech.kanguro.networking.dto.NearbySearchDto
import com.insurtech.kanguro.networking.dto.PlaceDetailSearchDto
import com.insurtech.kanguro.testing.extensions.mapJsonToModel
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkConstructor
import io.mockk.mockkObject
import io.mockk.runs
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class VetPlacesRepositoryTest {

    private val userLocation: Location = mockk()

    private lateinit var fakeVetPlacesDataSource: FakeVetPlacesDataSource

    private lateinit var vetPlacesRepository: IVetPlacesRepository

    @Before
    fun setUp() {
        mockkObject(DateUtils)

        every {
            DateUtils.getDayOfWeekOfToday()
        } returns 1

        mockkConstructor(Location::class)

        every {
            anyConstructed<Location>().latitude = any()
        } just runs

        every {
            anyConstructed<Location>().longitude = any()
        } just runs

        every {
            userLocation.distanceTo(any())
        } returns 100.0F

        fakeVetPlacesDataSource = FakeVetPlacesDataSource()

        vetPlacesRepository = VetPlacesRepository(
            vetPlacesDataSource = fakeVetPlacesDataSource
        )
    }

    @Test
    fun `Get place, when there was an unexpected error`() = runTest {
        // ARRANGE
        val expectedException = Exception("This is a custom exception", Throwable())

        fakeVetPlacesDataSource.setException(expectedException)

        // ACT
        vetPlacesRepository.getPlace(userLocation, "placeId", "key")
            .test {
                // ASSERT
                assertEquals(
                    expected = Result.Error(expectedException),
                    actual = awaitItem()
                )
                awaitComplete()
            }
    }

    @Test
    fun `Get nearby vet place, when there was an unexpected error`() = runTest {
        // ARRANGE
        val expectedException = Exception("This is a custom exception", Throwable())

        fakeVetPlacesDataSource.setException(expectedException)

        // ACT
        vetPlacesRepository.getNearbyVetPlaces(userLocation, "key")
            .test {
                // ASSERT
                assertEquals(
                    expected = Result.Error(expectedException),
                    actual = awaitItem()
                )
                awaitComplete()
            }
    }

    @Test
    fun `Get additional places, when there was an unexpected error`() = runTest {
        // ARRANGE
        val expectedException = Exception("This is a custom exception", Throwable())

        fakeVetPlacesDataSource.setException(expectedException)

        // ACT
        vetPlacesRepository.getAdditionalPlaces(userLocation, "nextToken", "key")
            .test {
                // ASSERT
                assertEquals(
                    expected = Result.Error(expectedException),
                    actual = awaitItem()
                )
                awaitComplete()
            }
    }

    @Test
    fun `Get place, successfully`() = runTest {
        // ARRANGE
        val placeDetailSearchDto = "200_get_get_place_successfully.json"
            .mapJsonToModel<PlaceDetailSearchDto>()

        if (placeDetailSearchDto != null) {
            val expectedVetPlace =
                VetPlaceMapper.mapFromPlaceResponse(placeDetailSearchDto.result, userLocation)

            fakeVetPlacesDataSource.setVetPlace(expectedVetPlace)

            // ACT
            vetPlacesRepository.getPlace(userLocation, "placeId", "key")
                .test {
                    // ASSERT
                    assertEquals(
                        expected = Result.Success(expectedVetPlace),
                        actual = awaitItem()
                    )
                    awaitComplete()
                }
        } else {
            fail("PlaceDetailSearchDto must not be null")
        }
    }

    @Test
    fun `Get nearby vet places, successfully`() = runTest {
        // ARRANGE
        val nearbyVetPlaceSearchDto = "200_get_get_nearby_search_successfully.json"
            .mapJsonToModel<NearbySearchDto>()

        if (nearbyVetPlaceSearchDto != null) {
            val expectedNearbyVetPlaceSearch =
                NearbyVetPlaceSearchMapper.mapNearBySearchDtoToNearbyVetPlaceSearchMapper(
                    nearbyVetPlaceSearchDto,
                    userLocation
                )

            fakeVetPlacesDataSource.setNearbyVetPlaceSearch(expectedNearbyVetPlaceSearch)

            // ACT
            vetPlacesRepository.getNearbyVetPlaces(userLocation, "key")
                .test {
                    // ASSERT
                    assertEquals(
                        expected = Result.Success(expectedNearbyVetPlaceSearch),
                        actual = awaitItem()
                    )
                    awaitComplete()
                }
        } else {
            fail("NearbyVetPlaceSearchDto must not be null")
        }
    }

    @Test
    fun `Get additional places, successfully`() = runTest {
        // ARRANGE
        val nearbyVetPlaceSearchDto = "200_get_get_nearby_search_successfully.json"
            .mapJsonToModel<NearbySearchDto>()

        if (nearbyVetPlaceSearchDto != null) {
            val expectedNearbyVetPlaceSearch =
                NearbyVetPlaceSearchMapper.mapNearBySearchDtoToNearbyVetPlaceSearchMapper(
                    nearbyVetPlaceSearchDto,
                    userLocation
                )

            fakeVetPlacesDataSource.setNearbyVetPlaceSearch(expectedNearbyVetPlaceSearch)

            // ACT
            vetPlacesRepository.getAdditionalPlaces(userLocation, "nextToken", "key")
                .test {
                    // ASSERT
                    assertEquals(
                        expected = Result.Success(expectedNearbyVetPlaceSearch),
                        actual = awaitItem()
                    )
                    awaitComplete()
                }
        } else {
            fail("NearbyVetPlaceSearchDto must not be null")
        }
    }
}
