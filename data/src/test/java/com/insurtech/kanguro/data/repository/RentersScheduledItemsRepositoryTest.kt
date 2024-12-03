package com.insurtech.kanguro.data.repository

import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.data.remote.fakes.FakeRentersScheduledItemsDataSource
import com.insurtech.kanguro.data.repository.impl.RentersScheduledItemsRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class RentersScheduledItemsRepositoryTest {

    private lateinit var fakeRentersScheduledItemsDataSource: FakeRentersScheduledItemsDataSource

    private lateinit var rentersScheduledItemsRepository: IRentersScheduledItemsRepository

    @Before
    fun setUp() {
        fakeRentersScheduledItemsDataSource = FakeRentersScheduledItemsDataSource()
        rentersScheduledItemsRepository = RentersScheduledItemsRepository(
            rentersScheduledItemsRemoteDataSource = fakeRentersScheduledItemsDataSource
        )
    }

    @Test
    fun `Put scheduled items images, successfully`() = runTest {
        // ACT
        val result = rentersScheduledItemsRepository.putImages("policy_id", emptyList())

        // ASSERT
        assertEquals(
            expected = Result.Success(Unit),
            actual = result
        )
    }

    @Test
    fun `Put scheduled items images, when there was an unexpected error`() = runTest {
        // ARRANGE
        val exception = Exception("This is a custom exception", Throwable())
        fakeRentersScheduledItemsDataSource.setException(exception)

        // ACT
        val result = rentersScheduledItemsRepository.putImages("policy_id", emptyList())

        // ASSERT
        assertEquals(
            expected = Result.Error(exception),
            actual = result
        )
    }
}
