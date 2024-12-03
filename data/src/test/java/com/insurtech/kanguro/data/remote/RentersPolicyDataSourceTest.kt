package com.insurtech.kanguro.data.remote

import com.insurtech.kanguro.data.source.RentersPolicyDataSource
import com.insurtech.kanguro.networking.api.KanguroRentersPolicyApiService
import com.insurtech.kanguro.networking.dto.ScheduledItemViewModelDto
import com.insurtech.kanguro.networking.error.RemoteServiceIntegrationError
import com.insurtech.kanguro.testing.BaseUnitTest
import com.insurtech.kanguro.testing.extensions.mapJsonToListOfModels
import com.insurtech.kanguro.testing.files.FileSystemSupport
import com.insurtech.kanguro.testing.rest.RestInfrastructureRule
import com.insurtech.kanguro.testing.rest.wireRestApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class RentersPolicyDataSourceTest : BaseUnitTest() {

    @get:Rule
    val restInfrastructureRule = RestInfrastructureRule()

    private lateinit var rentersPolicyDataSource: RentersPolicyDataSource

    @Before
    fun setUp() {
        val kanguroRentersPolicyApiService = restInfrastructureRule.server
            .wireRestApi<KanguroRentersPolicyApiService>()

        rentersPolicyDataSource = RentersPolicyRemoteDataSource(kanguroRentersPolicyApiService)
    }

    @Test
    fun `Get scheduled items, when it is requested to get scheduled items and there is no issue, then return scheduled items`() =
        runTest {
            // ARRANGE
            val scheduledItemsDtoFileName = "200_get_get_scheduled_items_successfully.json"

            restInfrastructureRule.restScenario(
                statusCode = 200,
                response = FileSystemSupport.loadFile(scheduledItemsDtoFileName)
            )

            val expectedScheduledItemsDto =
                scheduledItemsDtoFileName.mapJsonToListOfModels<ScheduledItemViewModelDto>()

            if (expectedScheduledItemsDto != null) {
                // ACT
                val scheduledItemsDto =
                    rentersPolicyDataSource.getScheduledItems(policyId = "policy_id")

                // ASSERT
                assertEquals(
                    expected = expectedScheduledItemsDto,
                    actual = scheduledItemsDto
                )
            } else {
                fail("ExpectedScheduledItemsDto must be not null.")
            }
        }

    @Test
    fun `Get scheduled items, when it is requested to get scheduled items and there is an issue incoming from client, then return client system error`() =
        runTest {
            // ARRANGE
            restInfrastructureRule.restScenario(
                statusCode = 400
            )

            // ACT
            val result = kotlin.runCatching {
                rentersPolicyDataSource.getScheduledItems(policyId = "policy_id")
            }

            // ASSERT
            val unwrappedError = unwrapCaughtError(result)

            assertEquals(
                expected = RemoteServiceIntegrationError.ClientOrigin,
                actual = unwrappedError
            )
        }

    @Test
    fun `Get scheduled items, when it is requested to get scheduled items and there is an issue incoming from server, then return remote system error`() =
        runTest {
            // ARRANGE
            restInfrastructureRule.restScenario(
                statusCode = 500
            )

            // ACT
            val result = kotlin.runCatching {
                rentersPolicyDataSource.getScheduledItems(policyId = "policy_id")
            }

            // ASSERT
            val unwrappedError = unwrapCaughtError(result)

            assertEquals(
                expected = RemoteServiceIntegrationError.RemoteSystem,
                actual = unwrappedError
            )
        }

    @Test
    fun `Delete scheduled item, when it is requested to delete scheduled item and there is no issue, then complete without error`() = runTest {
        // ARRANGE
        restInfrastructureRule.restScenario(
            statusCode = 200
        )

        // ACT
        val result = rentersPolicyDataSource.deleteScheduledItem(policyId = "policy_id", scheduledItemId = "scheduled_item_id")

        // ASSERT
        assertEquals(
            expected = Unit,
            actual = result
        )
    }

    @Test
    fun `Delete scheduled item, when it is requested to delete scheduled item and there is an issue incoming from client, then return client system error`() = runTest {
        // ARRANGE
        restInfrastructureRule.restScenario(
            statusCode = 400
        )

        // ACT
        val result = kotlin.runCatching {
            rentersPolicyDataSource.deleteScheduledItem(policyId = "policy_id", scheduledItemId = "scheduled_item_id")
        }

        // ASSERT
        val unwrappedError = unwrapCaughtError(result)

        assertEquals(
            expected = RemoteServiceIntegrationError.ClientOrigin,
            actual = unwrappedError
        )
    }

    @Test
    fun `Delete scheduled item, when it is requested to delete scheduled item and there is an issue incoming from server, then return remote system error`() = runTest {
        // ARRANGE
        restInfrastructureRule.restScenario(
            statusCode = 500
        )

        // ACT
        val result = kotlin.runCatching {
            rentersPolicyDataSource.deleteScheduledItem(policyId = "policy_id", scheduledItemId = "scheduled_item_id")
        }

        // ASSERT
        val unwrappedError = unwrapCaughtError(result)

        assertEquals(
            expected = RemoteServiceIntegrationError.RemoteSystem,
            actual = unwrappedError
        )
    }
}
