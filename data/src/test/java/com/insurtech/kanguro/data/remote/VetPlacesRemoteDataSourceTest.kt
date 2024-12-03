package com.insurtech.kanguro.data.remote

import android.location.Location
import com.insurtech.kanguro.common.date.DateUtils
import com.insurtech.kanguro.data.mapper.NearbyVetPlaceSearchMapper
import com.insurtech.kanguro.data.mapper.VetPlaceMapper
import com.insurtech.kanguro.data.source.VetPlacesDataSource
import com.insurtech.kanguro.networking.api.GooglePlacesApiService
import com.insurtech.kanguro.networking.dto.NearbySearchDto
import com.insurtech.kanguro.networking.dto.PlaceDetailSearchDto
import com.insurtech.kanguro.networking.error.RemoteServiceIntegrationError
import com.insurtech.kanguro.testing.BaseUnitTest
import com.insurtech.kanguro.testing.extensions.mapJsonToModel
import com.insurtech.kanguro.testing.files.FileSystemSupport
import com.insurtech.kanguro.testing.rest.RestInfrastructureRule
import com.insurtech.kanguro.testing.rest.wireRestApi
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkConstructor
import io.mockk.mockkObject
import io.mockk.runs
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class VetPlacesRemoteDataSourceTest : BaseUnitTest() {

    private val userLocation: Location = mockk()

    @get:Rule
    val restInfrastructureRule = RestInfrastructureRule()

    private lateinit var vetPlacesDataSource: VetPlacesDataSource

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

        every {
            userLocation.latitude
        } returns 0.0

        every {
            userLocation.longitude
        } returns 0.0

        val refactoredKanguroVetPlacesApiService = restInfrastructureRule.server
            .wireRestApi<GooglePlacesApiService>()

        vetPlacesDataSource = VetPlacesRemoteDataSource(refactoredKanguroVetPlacesApiService)
    }

    @Test
    fun `Get place, when requested to get place, then return place detail successfully `() =
        runTest {
            // ARRANGE
            val assetFileName = "200_get_get_place_successfully.json"

            restInfrastructureRule.restScenario(
                statusCode = 200,
                response = FileSystemSupport.loadFile(assetFileName)
            )

            val expectedPlaceDetail = assetFileName.mapJsonToModel<PlaceDetailSearchDto>()?.result

            if (expectedPlaceDetail != null) {
                // ACT
                val response = vetPlacesDataSource.getPlace(userLocation, "", "")

                // ASSERT
                assertEquals(
                    expected = VetPlaceMapper.mapFromPlaceResponse(
                        expectedPlaceDetail,
                        userLocation
                    ),
                    actual = response
                )
            } else {
                fail("ExpectedPlaceDetail must not be null")
            }
        }

    @Test
    fun `Get nearby vet places, when requested to get place, then return nearby vet place search successfully `() =
        runTest {
            // ARRANGE
            val assetFileName = "200_get_get_nearby_search_successfully.json"

            restInfrastructureRule.restScenario(
                statusCode = 200,
                response = FileSystemSupport.loadFile(assetFileName)
            )

            val expectedNearbySearchDto = assetFileName.mapJsonToModel<NearbySearchDto>()

            if (expectedNearbySearchDto != null) {
                // ACT
                val response = vetPlacesDataSource.getNearbyVetPlaces(userLocation, "key")

                // ASSERT
                assertEquals(
                    expected = NearbyVetPlaceSearchMapper.mapNearBySearchDtoToNearbyVetPlaceSearchMapper(
                        expectedNearbySearchDto,
                        userLocation
                    ),
                    actual = response
                )
            } else {
                fail("ExpectedPlaceDetail must not be null")
            }
        }

    @Test
    fun `Get additional places, when requested to get place, then return nearby vet place search successfully `() =
        runTest {
            // ARRANGE
            val assetFileName = "200_get_get_nearby_search_successfully.json"

            restInfrastructureRule.restScenario(
                statusCode = 200,
                response = FileSystemSupport.loadFile(assetFileName)
            )

            val expectedNearbySearchDto = assetFileName.mapJsonToModel<NearbySearchDto>()

            if (expectedNearbySearchDto != null) {
                // ACT
                val response =
                    vetPlacesDataSource.getAdditionalPlaces(userLocation, "nextToken", "key")

                // ASSERT
                assertEquals(
                    expected = NearbyVetPlaceSearchMapper.mapNearBySearchDtoToNearbyVetPlaceSearchMapper(
                        expectedNearbySearchDto,
                        userLocation
                    ),
                    actual = response
                )
            } else {
                fail("ExpectedPlaceDetail must not be null")
            }
        }

    @Test
    fun `Get place, when it it requested to get place and there is some issue incoming from client, then return client system error`() =
        runTest {
            // ARRANGE
            restInfrastructureRule.restScenario(400)

            // ACT
            val result = kotlin.runCatching {
                vetPlacesDataSource.getPlace(userLocation, "placeId", "key")
            }

            // ASSERT
            val unwrappedError = unwrapCaughtError(result)

            assertEquals(
                expected = RemoteServiceIntegrationError.ClientOrigin,
                actual = unwrappedError
            )
        }

    @Test
    fun `Get nearby vet places, when it it requested to get nearby vet places and there is some issue incoming from client, then return client system error`() =
        runTest {
            // ARRANGE
            restInfrastructureRule.restScenario(400)

            // ACT
            val result = kotlin.runCatching {
                vetPlacesDataSource.getNearbyVetPlaces(userLocation, "key")
            }

            // ASSERT
            val unwrappedError = unwrapCaughtError(result)

            assertEquals(
                expected = RemoteServiceIntegrationError.ClientOrigin,
                actual = unwrappedError
            )
        }

    @Test
    fun `Get additional places, when it it requested to get additional places and there is some issue incoming from client, then return client system error`() =
        runTest {
            // ARRANGE
            restInfrastructureRule.restScenario(400)

            // ACT
            val result = kotlin.runCatching {
                vetPlacesDataSource.getAdditionalPlaces(userLocation, "nextToken", "key")
            }

            // ASSERT
            val unwrappedError = unwrapCaughtError(result)

            assertEquals(
                expected = RemoteServiceIntegrationError.ClientOrigin,
                actual = unwrappedError
            )
        }

    @Test
    fun `Get place, when it it requested to get place and there is some issue incoming from server, then return remote system error`() =
        runTest {
            // ARRANGE
            restInfrastructureRule.restScenario(500)

            // ACT
            val result = kotlin.runCatching {
                vetPlacesDataSource.getPlace(userLocation, "placeId", "key")
            }

            // ASSERT
            val unwrappedError = unwrapCaughtError(result)

            assertEquals(
                expected = RemoteServiceIntegrationError.RemoteSystem,
                actual = unwrappedError
            )
        }

    @Test
    fun `Get additional places, when it it requested to get additional places and there is some issue incoming from server, then return remote system error`() =
        runTest {
            // ARRANGE
            restInfrastructureRule.restScenario(500)

            // ACT
            val result = kotlin.runCatching {
                vetPlacesDataSource.getAdditionalPlaces(userLocation, "nextToken", "key")
            }

            // ASSERT
            val unwrappedError = unwrapCaughtError(result)

            assertEquals(
                expected = RemoteServiceIntegrationError.RemoteSystem,
                actual = unwrappedError
            )
        }

    @Test
    fun `Get nearby vet places, when it it requested to get nearby vet places and there is some issue incoming from server, then return remote system error`() =
        runTest {
            // ARRANGE
            restInfrastructureRule.restScenario(500)

            // ACT
            val result = kotlin.runCatching {
                vetPlacesDataSource.getNearbyVetPlaces(userLocation, "key")
            }

            // ASSERT
            val unwrappedError = unwrapCaughtError(result)

            assertEquals(
                expected = RemoteServiceIntegrationError.RemoteSystem,
                actual = unwrappedError
            )
        }
}
