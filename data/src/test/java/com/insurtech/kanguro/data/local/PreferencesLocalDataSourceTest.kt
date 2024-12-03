package com.insurtech.kanguro.data.local

import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import com.insurtech.kanguro.common.enums.AppLanguage
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class PreferencesLocalDataSourceTest {

    private lateinit var preferencesLocalDataSource: PreferencesLocalDataSource

    private val mockedSharedPreferences: SharedPreferences = mockk()

    @Before
    fun setUp() {
        preferencesLocalDataSource = PreferencesLocalDataSource(
            preferences = mockedSharedPreferences
        )
    }

    @Test
    fun `Get preferred language, when it is requested to get the language, then returns the language successfully `() =
        runTest {
            // ARRANGE
            every {
                mockedSharedPreferences.getString(PREF_PREFERRED_LANGUAGE, null)
            } returns "Spanish"

            // ACT
            val preferredLanguage = preferencesLocalDataSource.getPreferredLanguage()

            // ASSERT
            assertEquals(
                expected = AppLanguage.Spanish,
                actual = preferredLanguage
            )
        }

    @Test
    fun `Set preferred language, when it is requested to set the language, then sets the language successfully`() =
        runTest {
            // ARRANGE
            val mockedEditor: Editor = mockk()

            every {
                mockedSharedPreferences.edit()
            } returns mockedEditor

            every {
                mockedEditor.putString(PREF_PREFERRED_LANGUAGE, AppLanguage.Spanish.name)
            } returns mockedEditor

            every {
                mockedEditor.commit()
            } returns true

            // ACT
            val isLanguageSet = preferencesLocalDataSource.setPreferredLanguage(AppLanguage.Spanish)

            // ASSERT
            assertEquals(
                expected = true,
                actual = isLanguageSet
            )
        }

    companion object {
        private const val PREF_PREFERRED_LANGUAGE = "user_preferred_language"
    }
}
