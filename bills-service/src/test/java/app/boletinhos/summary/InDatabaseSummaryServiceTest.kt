package app.boletinhos.summary

import app.boletinhos.fakes.SummaryFactory
import app.boletinhos.testutil.AppDatabaseTest
import assertk.assertAll
import assertk.assertThat
import assertk.assertions.containsAll
import assertk.assertions.containsExactly
import assertk.assertions.hasSize
import assertk.assertions.isEmpty
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class InDatabaseSummaryServiceTest : AppDatabaseTest() {
    @Test fun `should get summaries for a given batch of bills`() = runBlockingTest {
        val bills = SummaryFactory.bills

        bills.forEach { manageBillService.create(it) }

        summaryService.getSummary().test { summaries ->
            assertAll {
                assertThat(summaries).hasSize(3)
                assertThat(summaries).containsAll(
                    SummaryFactory.august,
                    SummaryFactory.december,
                    SummaryFactory.november
                )
            }
        }
    }

    @Test fun `should check if summaries are correctly ordered by its due date`() =
        runBlockingTest {
            val bills = SummaryFactory.bills

            bills.forEach { manageBillService.create(it) }

            summaryService.getSummary().test { summaries ->
                assertAll {
                    assertThat(summaries).hasSize(3)
                    assertThat(summaries).containsExactly(
                        SummaryFactory.december,
                        SummaryFactory.november,
                        SummaryFactory.august
                    )
                }
            }
        }

    @Test fun `should have no summary if there's no data in the data source`() = runBlockingTest {
        summaryService.getSummary().test { summaries ->
            assertThat(summaries).isEmpty()
        }
    }

    @Test fun `should be false when there is no bill to create a summary`() = runBlockingTest {
        assertThat(summaryService.hasSummary()).isFalse()
    }

    @Test fun `should be true when there is bill to create a summary`() = runBlockingTest {
        val bills = SummaryFactory.bills
        bills.forEach { manageBillService.create(it) }

        assertThat(summaryService.hasSummary()).isTrue()
    }
}