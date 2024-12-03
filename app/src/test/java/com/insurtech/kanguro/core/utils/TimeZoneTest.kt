package com.insurtech.kanguro.core.utils

import com.insurtech.kanguro.common.date.DateUtils.getFormattedLocalDate
import org.junit.Assert.assertEquals
import org.junit.BeforeClass
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

/**
 * TimeZone
 * https://developer.android.com/reference/java/util/TimeZone
 *
 * SimpleDateFormat
 * https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html
 */
class TimeZoneTest {

    private val dateUTC = Date(1685494800000) // Wednesday, May 31, 2023 1:00:00 AM
    private val dateString = "2023-01-23T23:51:12.0930184+00:00" // Monday, Jan 23, 2023 23:51:12 AM

    companion object {

        @BeforeClass
        @JvmStatic
        fun setUpClass() {
            Locale.setDefault(Locale.ENGLISH)
        }
    }

    @Test
    fun `Should show date formatted to UTC`() {
        val formattedDate =
            getFormattedLocalDate(
                dateUTC,
                "EEE, d MMM yyyy HH:mm:ss Z",
                TimeZone.getTimeZone("UTC")
            )

        assertEquals(
            "Wed, 31 May 2023 01:00:00 +0000",
            formattedDate
        )
    }

    @Test
    fun `Should show date formatted to Sao Paulo`() {
        val formattedDate = getFormattedLocalDate(
            dateUTC,
            "EEE, d MMM yyyy HH:mm:ss Z",
            TimeZone.getTimeZone("America/Sao_Paulo")
        )

        assertEquals(
            "Tue, 30 May 2023 22:00:00 -0300",
            formattedDate
        )
    }

    @Test
    fun `Should convert Local Date to String in UTC Time Zone `() {
        // In here is set the Time Zone as São Paulo.
        // It is telling that the date 2023-01-23 and hour 23:51:12 is in São Paulo.
        val date = dateString.toDate(timeZone = TimeZone.getTimeZone("America/Sao_Paulo"))

        val formattedDate = getFormattedLocalDate(
            date!!,
            "EEE, d MMM yyyy HH:mm:ss Z",
            TimeZone.getTimeZone("UTC")
        )

        assertEquals(
            "Tue, 24 Jan 2023 02:51:12 +0000",
            formattedDate

        )
    }

    @Test
    fun `Should convert Date in UTC to String in UTC Time Zone`() {
        val date = dateString.toDate()

        val formattedDate = getFormattedLocalDate(
            date!!,
            "EEE, d MMM yyyy HH:mm:ss Z",
            TimeZone.getTimeZone("UTC")
        )

        assertEquals(
            "Mon, 23 Jan 2023 23:51:12 +0000",
            formattedDate
        )
    }

    @Test
    fun `Should convert Date to String in Local Time Zone`() {
        val date = dateString.toDate()

        val formattedDate = getFormattedLocalDate(
            date!!,
            "EEE, d MMM yyyy HH:mm:ss Z",
            TimeZone.getTimeZone("America/Sao_Paulo")
        )

        assertEquals(
            "Mon, 23 Jan 2023 20:51:12 -0300",
            formattedDate
        )
    }

    /**
     * The "yyyy-MM-dd'T'HH:mm:ss" pattern ignores the seconds and the timezone. In this case one should
     * set the Time Zone when parsing from String to Date. If it is not set the SimpleDateFormat will
     * get the local time zone.
     *
     * The "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"  pattern considers seconds and time zone. So set the Time Zone
     * is not necessary
     *
     * Dates like "2023-01-23T23:51:12.093123+00:00" are NOT parsed right because SimpleDateFormat can not
     * parse correctly milliseconds
     */
    private fun String.toDate(
        dateFormat: String = "yyyy-MM-dd'T'HH:mm:ss",
        timeZone: TimeZone = TimeZone.getTimeZone("UTC")
    ): Date? {
        val parser = SimpleDateFormat(dateFormat, Locale.getDefault())
        parser.timeZone = timeZone
        return parser.parse(this)
    }
}
