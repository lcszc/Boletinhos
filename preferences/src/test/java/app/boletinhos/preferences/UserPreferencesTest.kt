package app.boletinhos.preferences

import assertk.assertThat
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import org.junit.After
import org.junit.Before
import org.junit.Test

class UserPreferencesTest {
    private val preferences = FakePreferences
    private lateinit var userPreferences: UserPreferences

    @Before fun setUp() {
        userPreferences = UserPreferences(preferences)
    }

    @After fun tearDown() {
        preferences.edit().clear().apply()
    }

    @Test fun `should crash reports be disabled by default`() {
        assertThat(userPreferences.isCrashReportEnabled).isFalse()
    }

    @Test fun `should enable crash reports`() {
        userPreferences.isCrashReportEnabled = true
        assertThat(userPreferences.isCrashReportEnabled).isTrue()
    }

    @Test fun `should disable crash reports`() {
        userPreferences.isCrashReportEnabled = true
        userPreferences.isCrashReportEnabled = false
        assertThat(userPreferences.isCrashReportEnabled).isFalse()
    }
}