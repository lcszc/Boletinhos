package app.boletinhos.bills

import app.boletinhos.fakes.BillsFactory
import app.boletinhos.testutil.AppDatabaseTest
import app.boletinhos.domain.bill.BillStatus.OVERDUE
import app.boletinhos.domain.bill.BillStatus.PAID
import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import java.time.LocalDate

class InDatabaseFetchBillServiceTest : AppDatabaseTest() {
    @Test fun `should get bills by its status`() = runBlockingTest {
        val expected = BillsFactory.paids

        (expected + BillsFactory.overdue).forEach {
            manageBillDao.create(it)
        }

        fetchBillService.getByStatus(PAID).test { actual ->
            assertThat(actual).isEqualTo(expected)
        }
    }

    @Test fun `should update a given bill`() = runBlockingTest {
        val bill = BillsFactory
            .pick()
            .first()
            .copy(dueDate = LocalDate.now().minusMonths(1), status = OVERDUE)

        manageBillDao.create(bill)

        val updated = fetchBillService.getById(id = 1)
            .copy(description = "My new description", paymentDate = LocalDate.now())
            .also { it.id = 1 }

        manageBillDao.pay(updated)

        assertThat(fetchBillService.getById(id = 1)).isEqualTo(updated)
    }
}