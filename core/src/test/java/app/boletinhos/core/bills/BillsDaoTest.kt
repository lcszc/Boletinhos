package app.boletinhos.core.bills

import app.boletinhos.core.factory.BillsFactory
import app.boletinhos.core.testutil.AppDatabaseTest
import app.boletinhos.domain.bill.Bill
import app.boletinhos.domain.bill.BillStatus.OVERDUE
import app.boletinhos.domain.bill.BillStatus.PAID
import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import java.time.LocalDate

class BillsDaoTest : AppDatabaseTest() {
    @Test fun `should insert bill`() = runBlockingTest {
        val expected = BillsFactory
            .pick()
            .first()

        billsDao.insert(expected.toEntity())

        assertThat(billsDao.getById(id = 1)).isEqualTo(expected)
    }

    @Test fun `should get bills by its status`() = runBlockingTest {
        val expected = BillsFactory.paids

        (expected + BillsFactory.overdue)
            .map(Bill::toEntity)
            .forEach { billsDao.insert(it) }

        billsDao.getByStatus(PAID).test { actual ->
            assertThat(actual).isEqualTo(expected)
        }
    }

    @Test fun `should update a given bill`() = runBlockingTest {
        val bill = BillsFactory
            .pick()
            .first()
            .toEntity()
            .copy(dueDate = LocalDate.now().minusMonths(1), status = OVERDUE)

        billsDao.insert(bill)

        val updated = billsDao.getById(id = 1)
            .copy(description = "My new description")
            .also { it.id = 1 }

        billsDao.update(updated.toEntity())

        assertThat(billsDao.getById(id = 1)).isEqualTo(updated)
    }
}