package com.insurtech.kanguro.data.remote

import com.insurtech.kanguro.common.enums.InformationTopics
import com.insurtech.kanguro.data.mapper.KanguroParameterMapper
import com.insurtech.kanguro.data.source.KanguroParameterDataSource
import com.insurtech.kanguro.networking.api.KanguroVetAdvicesApiService
import com.insurtech.kanguro.networking.dto.KanguroParameterDto
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

class KanguroParameterDataSourceTest : BaseUnitTest() {

    @get:Rule
    val restInfrastructureRule = RestInfrastructureRule()

    private lateinit var kanguroParameterDataSource: KanguroParameterDataSource

    @Before
    fun setup() {
        val kanguroVetAdvicesApiService = restInfrastructureRule.server
            .wireRestApi<KanguroVetAdvicesApiService>()

        kanguroParameterDataSource = KanguroParameterRemoteDataSource(
            kanguroVetAdvicesApiService
        )
    }

    @Test
    fun `Get pet advices, when it is requested to get pet advices, then returns the advices successfully`() =
        runTest {
            // ARRANGE
            val assetFileName = "200_get_get_vet_advices_successfully.json"
            restInfrastructureRule.restScenario(
                statusCode = 200,
                response = FileSystemSupport.loadFile(assetFileName)
            )

            val expectedResponse = assetFileName.mapJsonToListOfModels<KanguroParameterDto>()

            if (expectedResponse != null) {
                // ACT
                val response = kanguroParameterDataSource.getAdvices(InformationTopics.VetAdvice)

                // ASSERT
                assertEquals(
                    expected = KanguroParameterMapper.mapKanguroParameterDtosToKanguroParameters(
                        expectedResponse
                    ),
                    actual = response
                )
            }
        }

    @Test
    fun `Get pet advices, when it is request to get pet advices and there is some issue incoming from client, then return client system error`() =
        runTest {
            // ARRANGE
            restInfrastructureRule.restScenario(400)

            // ACT
            val result = kotlin.runCatching {
                kanguroParameterDataSource.getAdvices(InformationTopics.VetAdvice)
            }

            // ASSERT
            val unwrappedError = unwrapCaughtError(result)

            assertEquals(
                expected = RemoteServiceIntegrationError.ClientOrigin,
                actual = unwrappedError
            )
        }

    @Test
    fun `Get pet advices, when it is request to get pet advices and there is some issue incoming from server, then return remote system error`() =
        runTest {
            // ARRANGE
            restInfrastructureRule.restScenario(500)

            // ACT
            val result = kotlin.runCatching {
                kanguroParameterDataSource.getAdvices(InformationTopics.VetAdvice)
            }

            // ASSERT
            val unwrappedError = unwrapCaughtError(result)

            assertEquals(
                expected = RemoteServiceIntegrationError.RemoteSystem,
                actual = unwrappedError
            )
        }
}
