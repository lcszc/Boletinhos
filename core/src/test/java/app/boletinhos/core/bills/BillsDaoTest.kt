package app.boletinhos.core.bills

import app.boletinhos.core.factory.BillsFactory
import app.boletinhos.core.testutil.AppDatabaseTest
import app.boletinhos.domain.bill.status.BillStatus
import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class BillsDaoTest : AppDatabaseTest() {
    @Test fun `should insert and get inserted bill`() = runBlockingTest {
        // @given a unpaid bill
        val expected = BillsFactory.unpaid

        // @when inserting it to the app database
        billsDao.insert(expected.toEntity())

        // @then it should be added in the database
        billsDao.getAll().take(1).test { actual ->
            assertThat(actual.first()).isEqualTo(expected)
        }
    }

    @Test fun `should get bills by its status`() = runBlockingTest {
        // @given a list of bills
        val merged = BillsFactory.merged
        val expected = merged.filter { bill -> bill.status == BillStatus.UNPAID }

        // @and they are inserted in the database
        merged.forEach { bill ->
            billsDao.insert(bill.toEntity())
        }

        // @when fetching bills by `unpaid` status
        val actual = billsDao.getByStatus(BillStatus.UNPAID)

        // @then the list of fetched bills should be equal to the expected
        assertThat(actual).isEqualTo(expected)
    }
}