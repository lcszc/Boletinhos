package app.boletinhos.summary

import app.boletinhos.domain.summary.Summary
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import org.junit.After
import org.junit.Test
import java.time.Month

class UserSummaryPreferencesTest {
    private val preferences = FakePreferences
    private val summaryPreferences = UserSummaryPreferences(preferences)

    @After fun tearDown() {
        preferences.edit().clear().apply()
    }

    @Test fun `should be null id if there is no summary selected`() {
        assertThat(summaryPreferences.summaryId()).isNull()
    }

    @Test fun `should return current summary id`() {
        val id = Summary.idFrom(month = Month.FEBRUARY.value, year = 2019)
        summaryPreferences.summaryId(id)
        assertThat(summaryPreferences.summaryId()).isEqualTo(id)
    }
}
