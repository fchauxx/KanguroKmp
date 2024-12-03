package com.insurtech.kanguro.core.session

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import androidx.test.core.app.ApplicationProvider
import com.auth0.android.jwt.JWT
import com.insurtech.kanguro.common.enums.AppLanguage
import com.insurtech.kanguro.core.fakes.FakeTestApplication
import com.insurtech.kanguro.domain.model.Login
import com.insurtech.kanguro.networking.di.MoshiModule
import com.squareup.moshi.Moshi
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.Date

@RunWith(RobolectricTestRunner::class)
@Config(application = FakeTestApplication::class)
class SessionManagerTest {

    lateinit var sessionManager: SessionManager
    lateinit var sharedPreferences: SharedPreferences
    lateinit var moshi: Moshi

    private val testAccount = Login(
        id = "12345",
        givenName = "Tester",
        surname = "Surname",
        accessToken = JWT(
            "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6Ilg1ZVhrNHh5b2pORnVtMWtsMll0djhkbE5QNC1jNTdkTzZRR1RWQndhTmsifQ.eyJpc3MiOiJodHRwczovL2thbmd1cm9wb2F0ZWtkZXYuYjJjbG9naW4uY29tLzhjZWI2YmQ2LTE0NWMtNGM2MS1hOTg2LWIxZGExY2FiZGQ5Mi92Mi4wLyIsImV4cCI6MTY1MzM0MjU1MCwibmJmIjoxNjUzMzM4OTUwLCJhdWQiOiJiZTY0ZDhmYy0xMWQ5LTQxNDAtYjFjMi1hMmE2MmRhM2MxY2UiLCJpZHAiOiJMb2NhbEFjY291bnQiLCJvaWQiOiI5MWQxMzM5ZC00MTNjLTQzODAtOTAwZi01ODRlOWNhNzU5ZTgiLCJzdWIiOiI5MWQxMzM5ZC00MTNjLTQzODAtOTAwZi01ODRlOWNhNzU5ZTgiLCJnaXZlbl9uYW1lIjoiTGVvbmFyZG8iLCJmYW1pbHlfbmFtZSI6Ikd1YmVydCIsIm5hbWUiOiJMZW9uYXJkbyBHdWJlcnQiLCJlbWFpbHMiOlsibGVvbmFyZG8yLmNhcnZhbGhvQHBvYXRlay5jb20iXSwidGZwIjoiQjJDXzFfUk9QQ19BdXRoIiwic2NwIjoiYWNjZXNzX2FzX3VzZXIiLCJhenAiOiJiZTY0ZDhmYy0xMWQ5LTQxNDAtYjFjMi1hMmE2MmRhM2MxY2UiLCJ2ZXIiOiIxLjAiLCJpYXQiOjE2NTMzMzg5NTB9.kQ_OaIISsTIpSGmsWdYH6jaK8RaeYKbIM0cJN2lFwAlrlCv9Ta_PKb7U3EA1So7BV9Lqzo6cZPjvCKYH_lsHqDSqOcXj7dmvfKK5k4hJkGIcJ6-SnAfnDyLqVa09_MoHFJMoTuil3eB-_Q-MLcsSAzcBXnJtM5R3vAX0qZuQt7pmeFVtVs8Soae-4k-TgHU6pQ8YyPEFFh8_yfpd1E3yZAkkSfAO2ofKgEghi__W1Nv-7ivnagqDdWOql5-BMz_kKR_zrnlp2DkIIiZmYbGZinz-gmLGdUd6hYg5l5pt3m0UmIrYfw1pB-0IrET-JH507rcMurzaK5nEQgx__enMBw"
        ),
        language = AppLanguage.English,
        refreshToken = "eyJraWQiOiJjcGltY29yZV8wOTI1MjAxNSIsInZlciI6IjEuMCIsInppcCI6IkRlZmxhdGUiLCJzZXIiOiIxLjAifQ..FYALgC07v_aEKWYF.QBSakhzQsuVG1rH5hKO1nctL80knUzJU9HEIS7NNN1uXUue-eDEriVU2uWfYrZM18yW4XxTxb0zhnKn-p5BCeKgQCRtHcNvt2C_CdUyQEo6E2ayehPpt9DbsgXGZgleHNntOD8LS3OlZ5ndUpe4FoP7rhQFg1G7jVbE2F7nR3xmQKdizfbw1ixNERRUs7HLfW6AU4xyi53XUVT0WNEudzeOdVUIyR-lSwnYr_5ADdZggYq7sf7fRz_f3GlT8zXynvDYZrgnsKnxE-Q_ewqUAU1BbSN_wW2_eIiw94px4vtp5lXwwZA1CB6-1PZMcuqeSA4GuJW865VKkvG3Q3CE6LPiYBEyyUtVQz3LKLCCM0N-O5pNfrWMUoNGeC0sGgk2pVUGGHRPv2JQheuao75IbcqsRHuJ9_Kmi_xQdmga45as6G0D9QuiHPlpDiE02J4F32hbOexcjk2-YM78T-bdB7fZwpG5-8DkguNGtCHVDN2wcEz4Dm5laV_pB8l7wlIH72Opxm5y1cV9GhL6lzBbz7mlRaI3fCpan7AiCBh45NI40GdeBDDcdOJxCFnuXiIIVetz7XZ1c8AZXvZHDqcLE3ds6j1bAX97625LqRivf4ghAv6wwEgkoGFVMbDvjLLr177AXQXfwwfRT5in-i8hVT3O_nc70yCfdN1Q1IhsQhpdVyeIGFC1kk3rWKFCWUAvwylrux9CjM3Fd85Gva9rlPzprtfxv8R942wxZZxvSgbs5-xAG8Oxp3Q3SvOnv-N1xdPc.a9hv-spIIb1r0obc1-XU3g",
        idToken = JWT(
            "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6Ilg1ZVhrNHh5b2pORnVtMWtsMll0djhkbE5QNC1jNTdkTzZRR1RWQndhTmsifQ.eyJleHAiOjE2NTMzNDI1NTAsIm5iZiI6MTY1MzMzODk1MCwidmVyIjoiMS4wIiwiaXNzIjoiaHR0cHM6Ly9rYW5ndXJvcG9hdGVrZGV2LmIyY2xvZ2luLmNvbS84Y2ViNmJkNi0xNDVjLTRjNjEtYTk4Ni1iMWRhMWNhYmRkOTIvdjIuMC8iLCJzdWIiOiI5MWQxMzM5ZC00MTNjLTQzODAtOTAwZi01ODRlOWNhNzU5ZTgiLCJhdWQiOiJiZTY0ZDhmYy0xMWQ5LTQxNDAtYjFjMi1hMmE2MmRhM2MxY2UiLCJpYXQiOjE2NTMzMzg5NTAsImF1dGhfdGltZSI6MTY1MzMzODk1MCwiaWRwIjoiTG9jYWxBY2NvdW50Iiwib2lkIjoiOTFkMTMzOWQtNDEzYy00MzgwLTkwMGYtNTg0ZTljYTc1OWU4IiwiZ2l2ZW5fbmFtZSI6Ikxlb25hcmRvIiwiZmFtaWx5X25hbWUiOiJHdWJlcnQiLCJuYW1lIjoiTGVvbmFyZG8gR3ViZXJ0IiwiZW1haWxzIjpbImxlb25hcmRvMi5jYXJ2YWxob0Bwb2F0ZWsuY29tIl0sInRmcCI6IkIyQ18xX1JPUENfQXV0aCIsImF0X2hhc2giOiJTdkxWTnZ1bnJudHJraGJ5dVlpaFpnIn0.Kdm4_JoNRtA7hzWqYY4DARF2d8L0gqF9nokYHlmf2sAPGZllVoq6nFhB8tQJ2yf8TWuK08pr55jfYanrmghd5crxxUikQ7AMhSOsefuUqUm9T5RkUWr3Jrtm7QDeraPVFdpx6m-Migr9yoaFb2kg-3_kpOS0-X4ZvfJ7sbEBF2qnq0asNEr77y2IetY2swyexH9O4DNM-BeYRGXTQ4RuvCLl74Rn_9cPTq4rrbWRciLMwhwe5vNj51qaUnASiCDhpY5u-m4UMeZxPxEnjP0fXaEEXmURl6XHXQSEPcsjSgzO77i1io2-NuFE8u3yfebZ0v2MlgRQbZX_oVXeghs3sQ"
        ),
        referralCode = "ABC1234",
        phone = "1234567890",
        email = "user@test.com",
        isNeededDeleteData = false,
        isPasswordUpdateNeeded = false,
        donation = null,
        expiresOn = Date(1704067201) // Monday, January 1, 2024 12:00:01 AM
    )

    @Before
    fun setUp() {
        val context: Context = ApplicationProvider.getApplicationContext()
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        moshi = MoshiModule.provideMoshi()
        sessionManager = SessionManager(sharedPreferences, moshi)
    }

    @Test
    fun `No saved session returns null`() {
        assertNull(sessionManager.sessionInfo)
    }

    @Test
    fun `Saved session returns correct value`() {
        sharedPreferences.edit {
            putString(
                SessionManager.PREF_SESSION_INFO,
                moshi.adapter(Login::class.java).toJson(testAccount)
            )
        }
        assertEquals(testAccount.toString(), sessionManager.sessionInfo.toString())
    }

    @Test
    fun `Saving a session persists correctly`() {
        assertNull(sessionManager.sessionInfo)
        sessionManager.sessionInfo = testAccount
        val json = sharedPreferences.getString(SessionManager.PREF_SESSION_INFO, null)
        val persistedResponse = moshi.adapter(Login::class.java).fromJson(json!!)
        assertEquals(testAccount.toString(), persistedResponse.toString())
    }
}
