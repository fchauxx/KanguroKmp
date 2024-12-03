package com.insurtech.kanguro.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.insurtech.kanguro.core.fakes.FakeTestApplication
import com.insurtech.kanguro.core.repository.claims.IClaimsRepository
import com.insurtech.kanguro.ui.scenes.claims.ClaimsViewModel
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@Config(application = FakeTestApplication::class)
class ClaimsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var claimsViewModel: ClaimsViewModel
    private val claimsRepository: IClaimsRepository = mockk()

    // TODO: To review the implementation of this unit test, because it is flaky, preferably, after the scaffolding.
    /*@Test
    fun `test loadClaims and refreshClaims`() = runTest {
        // Arrange
        val claimsList = listOf(
            Claim(id = "1", status = ClaimStatus.Submitted),
            Claim(id = "2", status = ClaimStatus.Closed),
            Claim(id = "3", status = ClaimStatus.Draft)
        )
        coEvery { claimsRepository.getClaims() } returns NetworkResponse.Success(
            claimsList,
            response = Response.success(claimsList)
        )

        val openClaims = listOf(claimsList[0])
        val closedClaims = listOf(claimsList[1])
        val draftClaims = listOf(claimsList[2])

        // Act
        claimsViewModel = ClaimsViewModel(claimsRepository)

        // Assert
        coVerify { claimsRepository.getClaims() }

        // Test openClaims
        claimsViewModel.setFilteredList(R.id.openButton)
        assertEquals(openClaims, claimsViewModel.displayedList.getOrAwaitValue())

        // Test closedClaims
        claimsViewModel.setFilteredList(R.id.closedButton)
        assertEquals(closedClaims, claimsViewModel.displayedList.getOrAwaitValue())

        // Test draftClaims
        claimsViewModel.setFilteredList(R.id.draftsButton)
        assertEquals(draftClaims, claimsViewModel.displayedList.getOrAwaitValue())
    }*/

    @Test
    fun `test loadClaims and getClaims error`() = runTest {
        /*
        // Arrange
        coEvery { claimsRepository.getClaims() } returns NetworkResponse.ServerError(
            null,
            Response.error<ErrorDto>(400, "".toResponseBody())
        )
        val networkErrorObserver = mockk<Observer<ErrorWithRetry<*>>>(relaxed = true)

        // Act
        claimsViewModel = ClaimsViewModel(claimsRepository)
        claimsViewModel.networkError.observeForever(networkErrorObserver)

        // Assert
        coVerify { claimsRepository.getClaims() }
        verify { networkErrorObserver.onChanged(any()) }
        */
    }
}
