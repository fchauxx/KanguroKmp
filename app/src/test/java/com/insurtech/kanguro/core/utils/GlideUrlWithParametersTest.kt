package com.insurtech.kanguro.core.utils

import com.insurtech.kanguro.core.fakes.FakeTestApplication
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(application = FakeTestApplication::class)
class GlideUrlWithParametersTest {

    @Test
    fun testCustomKey() {
        val url = GlideUrlWithParameters("https://www.google.com/search?q=queries+parameters&ei=BAJ8YordC8zb1sQPq52q0Aw&ved=0ahUKEwiK75Gui9j3AhXMrZUCHauOCsoQ4dUDCA4&uact=5&oq=android+get+url+without+queries+parameters&gs_lcp=Cgdnd3Mtd2l6EAMyBwghEAoQoAE6BAgAEEc6BQghEKABOggIIRAWEB0QHjoKCCEQFhAKEB0QHkoECEEYAEoECEYYAFDJDFj1FmDMF2gAcAJ4AIABnAGIAaQLkgEEMC4xMJgBAKABAcgBCMABAQ&sclient=gws-wiz")
        assertEquals("https://www.google.com/search", url.cacheKey)
    }
}
