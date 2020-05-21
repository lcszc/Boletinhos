package app.boletinhos.storage.bills

import app.boletinhos.storage.factory.SummaryFactory
import app.boletinhos.storage.testutil.AppDatabaseTest
import app.boletinhos.domain.bill.Bill
import assertk.assertAll
import assertk.assertThat
import assertk.assertions.containsAll
import assertk.assertions.containsExactly
import assertk.assertions.hasSize
import assertk.assertions.isEmpty
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class BillsSummaryDaoTest : AppDatabaseTest() {
    @Test fun `should get summaries for a given batch of bills`() = runBlockingTest {
        val bills = SummaryFactory.bills

        bills.map(Bill::toEntity).forEach { billsDao.insert(it) }

        billsSummaryDao.getSummary().test { summaries ->
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

            bills.map(Bill::toEntity).forEach { billsDao.insert(it) }

            billsSummaryDao.getSummary().test { summaries ->
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
        billsSummaryDao.getSummary().test { summaries ->
            assertThat(summaries).isEmpty()
        }
    }
}