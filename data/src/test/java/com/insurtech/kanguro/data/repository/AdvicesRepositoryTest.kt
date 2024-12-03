package com.insurtech.kanguro.data.repository

import app.cash.turbine.test
import com.insurtech.kanguro.common.enums.InformationTopics
import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.data.mapper.KanguroParameterMapper
import com.insurtech.kanguro.data.remote.fakes.FakeKanguroParameterDataSource
import com.insurtech.kanguro.data.repository.impl.AdvicesRepository
import com.insurtech.kanguro.networking.dto.KanguroParameterDto
import com.insurtech.kanguro.testing.extensions.mapJsonToListOfModels
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class AdvicesRepositoryTest {

    private lateinit var fakeKanguroParameterRemoteDataSource: FakeKanguroParameterDataSource

    private lateinit var vetAdvicesRepository: IAdvicesRepository

    @Before
    fun setup() {
        fakeKanguroParameterRemoteDataSource = FakeKanguroParameterDataSource()
        vetAdvicesRepository = AdvicesRepository(
            kanguroParameterDataSource = fakeKanguroParameterRemoteDataSource
        )
    }

    @Test
    fun `Get vet advices, when it is requested to get advices, then return success with vet advices`() =
        runTest {
            // ARRANGE
            val expectedAdvicesDto =
                "200_get_get_vet_advices_successfully.json".mapJsonToListOfModels<KanguroParameterDto>()

            if (expectedAdvicesDto != null) {
                val expectedAdvices =
                    KanguroParameterMapper.mapKanguroParameterDtosToKanguroParameters(
                        expectedAdvicesDto
                    )
                fakeKanguroParameterRemoteDataSource.setKanguroParameters(expectedAdvices)

                // ACT
                vetAdvicesRepository.getAdvices(InformationTopics.VetAdvice).test {
                    // ASSERT
                    assertEquals(
                        expected = Result.Success(expectedAdvices),
                        actual = awaitItem()
                    )
                    awaitComplete()
                }
            } else {
                fail("ExpectedAdvicesDto must be not null.")
            }
        }

    @Test
    fun `Get vet advices, when it is requested to get advices when there was a unexpected error, then return result error`() =
        runTest {
            // ARRANGE
            val exception = Exception("This is a custom exception", Throwable())
            fakeKanguroParameterRemoteDataSource.setException(exception)

            // ACT
            vetAdvicesRepository.getAdvices(InformationTopics.VetAdvice).test {
                // ASSERT
                assertEquals(
                    expected = Result.Error(exception),
                    actual = awaitItem()
                )
                awaitComplete()
            }
        }
}
