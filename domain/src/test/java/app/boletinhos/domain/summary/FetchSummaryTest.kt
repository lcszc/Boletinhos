package app.boletinhos.domain.summary

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isNotEqualTo
import assertk.assertions.isTrue
import assertk.fail
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import testutil.MainCoroutineRule
import java.time.Month

class FetchSummaryTest {
    @get:Rule val rule = MainCoroutineRule()

    private val coroutineScope = rule.testScope

    @Test fun `should return empty flow if there is no bills to summarize`() {
        runBlocking {
            val preferences = FakeSummaryPreferences()
            val service = FakeSummaryService(emptyList())
            val fetchSummary = FetchSummary(preferences, service)

            var isEmpty = false
            fetchSummary().onEmpty { isEmpty = true }.launchIn(coroutineScope)

            assertThat(isEmpty).isTrue()
        }
    }

    @Test fun `should return last selected user's summary`() {
        runBlocking {
            val january = Summary(
                month = Month.NOVEMBER,
                year = 2020,
                totalValue = 0L,
                paids = 0,
                unpaids = 0,
                overdue = 0
            )

            val february = january.copy(month = Month.FEBRUARY)
            val march = january.copy(month = Month.MARCH)

            val preferences = FakeSummaryPreferences()
            preferences.actualSummary(february.id())
            val service = FakeSummaryService(listOf(january, february, march))
            val fetchSummary = FetchSummary(preferences, service)

            fetchSummary().onEmpty {
                fail("This flow should return february's summary.")
            }.onEach { summary ->
                assertThat(summary).isEqualTo(february)
            }.launchIn(coroutineScope)
        }
    }

    @Test fun `should return first summary in the data source if user has not selected any`() {
        runBlocking {
            val january = Summary(
                month = Month.NOVEMBER,
                year = 2020,
                totalValue = 0L,
                paids = 0,
                unpaids = 0,
                overdue = 0
            )

            val february = january.copy(month = Month.FEBRUARY)
            val march = january.copy(month = Month.MARCH)

            val preferences = FakeSummaryPreferences()
            val service = FakeSummaryService(listOf(january, february, march))
            val fetchSummary = FetchSummary(preferences, service)

            fetchSummary().onEmpty {
                fail("This flow should return january's summary.")
            }.onEach { summary ->
                assertThat(summary).isEqualTo(january)
            }.launchIn(coroutineScope)
        }
    }

    @Test fun `should mark fetched summary as the actual summary  on preferences`() {
        runBlocking {
            val january = Summary(
                month = Month.NOVEMBER,
                year = 2020,
                totalValue = 0L,
                paids = 0,
                unpaids = 0,
                overdue = 0
            )

            val february = january.copy(month = Month.FEBRUARY)
            val march = january.copy(month = Month.MARCH)

            val preferences = FakeSummaryPreferences()
            val service = FakeSummaryService(listOf(january, february, march))
            val fetchSummary = FetchSummary(preferences, service)

            fetchSummary().launchIn(coroutineScope)

            assertThat(preferences.actualSummaryId()).isEqualTo(january.id())
        }
    }
}
