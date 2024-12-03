package com.insurtech.kanguro.data.remote

import com.insurtech.kanguro.data.source.RentersScheduledItemsDataSource
import com.insurtech.kanguro.networking.api.KanguroRentersScheduledItemsApiService
import com.insurtech.kanguro.networking.error.RemoteServiceIntegrationError
import com.insurtech.kanguro.testing.BaseUnitTest
import com.insurtech.kanguro.testing.rest.RestInfrastructureRule
import com.insurtech.kanguro.testing.rest.wireRestApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

class RentersScheduledItemsDataSourceTest : BaseUnitTest() {

    @get:Rule
    val restInfrastructureRule = RestInfrastructureRule()

    private lateinit var rentersScheduledItemsDataSource: RentersScheduledItemsDataSource

    @Before
    fun setUp() {
        val kanguroRentersPolicyApiService = restInfrastructureRule.server
            .wireRestApi<KanguroRentersScheduledItemsApiService>()

        rentersScheduledItemsDataSource =
            RentersScheduledItemsRemoteDataSource(kanguroRentersPolicyApiService)
    }

    @Test
    fun `Put scheduled item images, when it is requested to put scheduled item images and there is no issue, then complete without error`() =
        runTest {
            // ARRANGE
            restInfrastructureRule.restScenario(
                statusCode = 200
            )

            // ACT
            val result =
                rentersScheduledItemsDataSource.putImages(scheduledItemId = "scheduled_item_id", images = listOf())

            // ASSERT
            assertEquals(
                expected = Unit,
                actual = result
            )
        }

    @Test
    fun `Put scheduled item images, when it is requested to put scheduled item images and there is an issue incoming from client, then return client system error`() =
        runTest {
            // ARRANGE
            restInfrastructureRule.restScenario(
                statusCode = 400
            )

            // ACT
            val result = kotlin.runCatching {
                rentersScheduledItemsDataSource.putImages(
                    scheduledItemId = "scheduled_item_id",
                    images = listOf()
                )
            }

            // ASSERT
            val unwrappedError = unwrapCaughtError(result)

            assertEquals(
                expected = RemoteServiceIntegrationError.ClientOrigin,
                actual = unwrappedError
            )
        }

    @Test
    fun `Put scheduled item images, when it is requested to put scheduled item images and there is an issue incoming from server, then return remote system error`() =
        runTest {
            // ARRANGE
            restInfrastructureRule.restScenario(
                statusCode = 500
            )

            // ACT
            val result = kotlin.runCatching {
                rentersScheduledItemsDataSource.putImages(
                    scheduledItemId = "scheduled_item_id",
                    images = listOf()
                )
            }

            // ASSERT
            val unwrappedError = unwrapCaughtError(result)

            assertEquals(
                expected = RemoteServiceIntegrationError.RemoteSystem,
                actual = unwrappedError
            )
        }
}
