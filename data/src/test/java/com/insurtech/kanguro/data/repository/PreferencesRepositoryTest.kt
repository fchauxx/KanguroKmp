package com.insurtech.kanguro.data.repository

import com.insurtech.kanguro.common.enums.AppLanguage
import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.data.local.fakes.FakePreferencesLocalDataSource
import com.insurtech.kanguro.data.repository.impl.PreferencesRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class PreferencesRepositoryTest {

    private lateinit var fakePreferencesLocalDataSource: FakePreferencesLocalDataSource

    private lateinit var preferencesRepository: IPreferencesRepository

    @Before
    fun setUp() {
        fakePreferencesLocalDataSource = FakePreferencesLocalDataSource()

        preferencesRepository = PreferencesRepository(
            preferencesLocalDataSource = fakePreferencesLocalDataSource
        )
    }

    @Test
    fun `Get preferred language, when it is requested, then returns preferred language`() =
        runTest {
            // ARRANGE
            val expectedPreferredLanguage = AppLanguage.Spanish

            fakePreferencesLocalDataSource.setFakePreferredLanguage(expectedPreferredLanguage)

            // ACT
            val preferredLanguage = preferencesRepository.getPreferredLanguage()

            // ASSERT
            assertEquals(
                expected = Result.Success(expectedPreferredLanguage),
                actual = preferredLanguage
            )
        }

    @Test
    fun `Set preferred language, when it is passed a preferred language, then returns success with true`() =
        runTest {
            // ARRANGE
            val preferredLanguage = AppLanguage.Spanish

            fakePreferencesLocalDataSource.setPreferredLanguageSuccess(true)

            // ACT
            val result = preferencesRepository.setPreferredLanguage(preferredLanguage)

            // ASSERT
            assertEquals(
                expected = Result.Success(true),
                actual = result
            )
        }

    @Test
    fun `Get preferred language, when it is requested and there as an unexpected error, then returns error`() =
        runTest {
            // ARRANGE
            val expectedException = Exception("This is a custom exception", Throwable())

            fakePreferencesLocalDataSource.setException(expectedException)

            // ACT
            val result = preferencesRepository.getPreferredLanguage()

            // ASSERT
            assertEquals(
                expected = Result.Error(expectedException),
                actual = result
            )
        }

    @Test
    fun `Set preferred language, when it is passed a preferred language and there as an unexpected error, then returns success false`() =
        runTest {
            // ARRANGE
            val expectedException = Exception("This is a custom exception", Throwable())

            fakePreferencesLocalDataSource.setException(expectedException)

            // ACT
            val result = preferencesRepository.setPreferredLanguage(AppLanguage.Spanish)

            // ASSERT
            assertEquals(
                expected = Result.Success(false),
                actual = result
            )
        }
}
