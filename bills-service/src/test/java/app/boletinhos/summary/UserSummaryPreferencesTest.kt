package app.boletinhos.summary

import app.boletinhos.domain.summary.Summary
import app.boletinhos.domain.summary.SummaryPreferences
import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.After
import org.junit.Test
import java.time.Month

class UserSummaryPreferencesTest {
    private val preferences = FakePreferences
    private val summaryPreferences = UserSummaryPreferences(preferences)

    @After fun tearDown() {
        preferences.edit().clear().apply()
    }

    @Test fun `should return NO_SUMMARY id if there is no summary marked as actual`() {
        assertThat(summaryPreferences.actualSummaryId()).isEqualTo(SummaryPreferences.NO_SUMMARY)
    }

    @Test fun `should return actual summary ID`() {
        val id = Summary.idFrom(month = Month.FEBRUARY.value, year = 2019)
        summaryPreferences.actualSummary(id)
        assertThat(summaryPreferences.actualSummaryId()).isEqualTo(id)
    }
}