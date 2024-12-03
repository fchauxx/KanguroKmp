package com.insurtech.kanguro.data.remote

import com.insurtech.kanguro.data.mapper.BackendVersionMapper
import com.insurtech.kanguro.data.source.BackendVersionDataSource
import com.insurtech.kanguro.networking.api.KanguroApiVersionService
import com.insurtech.kanguro.networking.dto.BackendVersionDto
import com.insurtech.kanguro.networking.error.RemoteServiceIntegrationError
import com.insurtech.kanguro.testing.BaseUnitTest
import com.insurtech.kanguro.testing.files.FileSystemSupport
import com.insurtech.kanguro.testing.rest.RestInfrastructureRule
import com.insurtech.kanguro.testing.rest.wireRestApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

class BackendVersionRemoteDataSourceTest : BaseUnitTest() {

    @get:Rule val restInfrastructureRule = RestInfrastructureRule()

    private lateinit var backendVersionDataSource: BackendVersionDataSource

    @Before fun setUp() {
        val kanguroApiVersionService = restInfrastructureRule.server
            .wireRestApi<KanguroApiVersionService>()

        backendVersionDataSource = BackendVersionRemoteDataSource(
            kanguroApiVersionService
        )
    }

    @Test fun `Get backend version, when it is requested to get backend version and there is some issue incoming from client, then return client system error`() = runTest {
        // ARRANGE

        restInfrastructureRule.restScenario(400)

        // ACT

        val result = kotlin.runCatching {
            backendVersionDataSource.getBackendVersion()
        }

        // ASSERT

        val unwrappedError = unwrapCaughtError(result)

        assertEquals(
            expected = RemoteServiceIntegrationError.ClientOrigin,
            actual = unwrappedError
        )
    }

    @Test fun `Get backend version, when it is requested to get backend version and there is some issue incoming from server, then return remote system error`() = runTest {
        // ARRANGE

        restInfrastructureRule.restScenario(500)

        // ACT

        val result = kotlin.runCatching {
            backendVersionDataSource.getBackendVersion()
        }

        // ASSERT

        val unwrappedError = unwrapCaughtError(result)

        assertEquals(
            expected = RemoteServiceIntegrationError.RemoteSystem,
            actual = unwrappedError
        )
    }

    @Test fun `Get backend version, when it is requested to get backend version, then returns backend version successfully`() = runTest {
        // ARRANGE

        restInfrastructureRule.restScenario(
            statusCode = 200,
            response = FileSystemSupport.loadFile("200_get_get_backend_version_successfully.json")
        )

        val expectedResponse = BackendVersionDto("7.0.0")

        // ACT

        val response = backendVersionDataSource.getBackendVersion()

        // ASSERT

        assertEquals(
            expected = BackendVersionMapper.mapBackendVersionDtoToBackendVersion(expectedResponse),
            actual = response
        )
    }
}
