package com.insurtech.kanguro.data.repository

import app.cash.turbine.test
import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.data.mapper.PetMapper
import com.insurtech.kanguro.data.remote.fakes.FakePetRemoteDataSource
import com.insurtech.kanguro.data.repository.impl.RefactoredPetRepository
import com.insurtech.kanguro.networking.dto.PetDto
import com.insurtech.kanguro.testing.extensions.mapJsonToModel
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class PetRepositoryTest {

    private lateinit var fakePetRemoteDataSource: FakePetRemoteDataSource

    private lateinit var petRepository: IRefactoredPetRepository

    @Before
    fun setUp() {
        fakePetRemoteDataSource = FakePetRemoteDataSource()
        petRepository = RefactoredPetRepository(
            petRemoteDataSource = fakePetRemoteDataSource
        )
    }

    @Test
    fun `Get pet detail, successfully`() = runTest {
        // ARRANGE
        val petDto = "200_get_get_pet_detail_successfully.json"
            .mapJsonToModel<PetDto>()

        if (petDto != null) {
            val expectedPet = PetMapper.mapPetDtoToPet(petDto)

            fakePetRemoteDataSource.setPet(expectedPet)

            // ACT
            petRepository.getPetDetail(1L)
                .test {
                    // ASSERT
                    assertEquals(
                        expected = Result.Success(expectedPet),
                        actual = awaitItem()
                    )
                    awaitComplete()
                }
        } else {
            fail("PetDto must no be null")
        }
    }

    @Test
    fun `Get pet detail, when there was an unexpected error`() = runTest {
        // ARRANGE
        val exception = Exception("This is a custom exception", Throwable())
        fakePetRemoteDataSource.setException(exception)

        // ACT
        petRepository.getPetDetail(1L)
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
