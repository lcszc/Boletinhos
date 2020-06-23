package app.boletinhos.bill

import app.boletinhos.domain.bill.BillStatus
import app.boletinhos.fakes.BillsFactory
import app.boletinhos.testutil.AppDatabaseTest
import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import java.time.LocalDate

class InDatabaseManageBillServiceTest : AppDatabaseTest() {
    @Test fun `should create new bill`() = runBlockingTest {
        val expected = BillsFactory
            .pick()
            .first()

        manageBillService.create(expected)

        assertThat(billService.getById(id = 1)).isEqualTo(expected)
    }

    @Test fun `should mark a given bill as paid`() = runBlockingTest {
        val bill = BillsFactory
            .pick()
            .first()
            .copy(dueDate = LocalDate.now().minusMonths(1), status = BillStatus.OVERDUE)

        manageBillService.create(bill)
        manageBillService.create(bill.copy(name = "Legal"))

        val billToPay = billService.getById(id = 1)
            .copy(paymentDate = LocalDate.now(), status = BillStatus.PAID)
            .also { it.id = 1 }

        manageBillService.pay(billToPay)

        assertThat(billService.getById(id = 1)).isEqualTo(billToPay)
    }
}