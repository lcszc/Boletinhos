package app.boletinhos.core.bills

import app.boletinhos.core.factory.BillsFactory
import app.boletinhos.core.testutil.AppDatabaseTest
import app.boletinhos.domain.bill.BillStatus.OVERDUE
import app.boletinhos.domain.bill.BillStatus.PAID
import app.boletinhos.domain.bill.BillStatus.UNPAID
import assertk.assertAll
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import java.time.LocalDate

class BillsRepositoryTest : AppDatabaseTest() {
    private val repository by lazy {
        BillsRepository(billsDao)
    }

    @Test fun `should create new bill with unpaid status`() = runBlockingTest {
        val bill = BillsFactory.pick().first()

        repository.new(bill)

        val actual = billsDao.getById(id = 1)
        assertThat(actual.status).isEqualTo(UNPAID)
    }

    @Test fun `should create new bill with overdue status`() = runBlockingTest {
        val bill = BillsFactory.pick().first().copy(dueDate = LocalDate.now().minusMonths(3))

        repository.new(bill)

        val actual = billsDao.getById(id = 1)
        assertThat(actual.status).isEqualTo(OVERDUE)
    }

    @Test fun `should mark given bill as paid`() = runBlockingTest {
        val bill = BillsFactory.pick()
            .first()
            .also { it.id = 1 }

        repository.run {
            new(bill)
            pay(bill)
        }

        val actual = billsDao.getById(id = 1)

        assertAll {
            assertThat(actual.paymentDate).isNotNull().isEqualTo(LocalDate.now())
            assertThat(actual.status).isEqualTo(PAID)
        }
    }
}