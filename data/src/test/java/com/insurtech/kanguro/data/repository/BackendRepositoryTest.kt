package com.insurtech.kanguro.data.repository

import app.cash.turbine.test
import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.data.remote.fakes.FakeBackendVersionRemoteDataSource
import com.insurtech.kanguro.data.repository.impl.BackendVersionRepository
import com.insurtech.kanguro.domain.model.BackendVersion
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class BackendRepositoryTest {

    private lateinit var fakeBackendVersionRemoteDataSource: FakeBackendVersionRemoteDataSource

    private lateinit var backendVersionRepository: IBackendVersionRepository

    @Before fun setUp() {
        fakeBackendVersionRemoteDataSource = FakeBackendVersionRemoteDataSource()

        backendVersionRepository = BackendVersionRepository(fakeBackendVersionRemoteDataSource)
    }

    @Test fun `Get backend version, when it is requested to get backend version and there was an unexpected error, then return result error`() = runTest {
        // ARRANGE

        val expectedException = Exception("This is a custom exception", Throwable())

        fakeBackendVersionRemoteDataSource.setException(expectedException)

        // ACT / ASSERT

        backendVersionRepository.getBackendVersion().test {
            assertEquals(
                expected = Result.Error(expectedException),
                actual = awaitItem()
            )
            awaitComplete()
        }
    }

    @Test fun `Get backend version, when it is requested to get backend version, then return result success with backend version`() = runTest {
        // ARRANGE

        val expectedBackendVersion = BackendVersion("7.0.0")

        fakeBackendVersionRemoteDataSource.setBackendVersion(expectedBackendVersion)

        // ACT / ASSERT

        backendVersionRepository.getBackendVersion().test {
            assertEquals(
                expected = Result.Success(expectedBackendVersion),
                actual = awaitItem()
            )
            awaitComplete()
        }
    }
}
