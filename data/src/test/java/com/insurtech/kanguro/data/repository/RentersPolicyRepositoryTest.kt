package com.insurtech.kanguro.data.repository

import app.cash.turbine.test
import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.data.mapper.ScheduledItemMapper
import com.insurtech.kanguro.data.remote.fakes.FakeRentersPolicyDataSource
import com.insurtech.kanguro.data.repository.impl.RentersPolicyRepository
import com.insurtech.kanguro.networking.dto.ScheduledItemViewModelDto
import com.insurtech.kanguro.testing.extensions.mapJsonToListOfModels
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class RentersPolicyRepositoryTest {

    private lateinit var fakeRentersPolicyDataSource: FakeRentersPolicyDataSource

    private lateinit var rentersPolicyRepository: IRentersPolicyRepository

    @Before
    fun setUp() {
        fakeRentersPolicyDataSource = FakeRentersPolicyDataSource()
        rentersPolicyRepository = RentersPolicyRepository(
            rentersPolicyRemoteDataSource = fakeRentersPolicyDataSource
        )
    }

    @Test
    fun `Get scheduled items, successfully`() = runTest {
        // ARRANGE
        val scheduledItemsDto =
            "200_get_get_scheduled_items_successfully.json".mapJsonToListOfModels<ScheduledItemViewModelDto>()

        if (scheduledItemsDto != null) {
            val expectedScheduledItems =
                ScheduledItemMapper.mapScheduledItemsDtosToScheduledItems(scheduledItemsDto)

            fakeRentersPolicyDataSource.setScheduledItems(scheduledItemsDto)

            // ACT
            rentersPolicyRepository.getScheduledItems("policy_id")
                .test {
                    // ASSERT
                    assertEquals(
                        expected = Result.Success(expectedScheduledItems),
                        actual = awaitItem()
                    )
                    awaitComplete()
                }
        } else {
            fail("ScheduledItemsDto must no be null")
        }
    }

    @Test
    fun `Get scheduled items, when there was an unexpected error`() = runTest {
        // ARRANGE
        val exception = Exception("This is a custom exception", Throwable())
        fakeRentersPolicyDataSource.setException(exception)

        // ACT
        rentersPolicyRepository.getScheduledItems("policy_id")
            .test {
                // ASSERT
                assertEquals(
                    expected = Result.Error(exception),
                    actual = awaitItem()
                )
                awaitComplete()
            }
    }
}
