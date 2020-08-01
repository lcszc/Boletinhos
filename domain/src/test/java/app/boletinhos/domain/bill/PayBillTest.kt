package app.boletinhos.domain.bill

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFailure
import assertk.assertions.isTrue
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.time.LocalDate

class PayBillTest {
    private val gateway = FakeManageBillService()
    private val createBill = CreateBill(gateway)
    private val payBill = PayBill(gateway)

    private val fakeBill = Bill(
        name = "Personal expense",
        description = "Food Category",
        value = 250_00L,
        paymentDate = null,
        dueDate = LocalDate.now(),
        status = BillStatus.UNPAID
    )

    @Test
    fun `should mark bill as paid`() = runBlocking {
        val bill = fakeBill
        bill.id = 100

        createBill(bill)
        payBill(bill)

        val actual = gateway.bills.getValue(100)

        assertThat(actual.status).isEqualTo(BillStatus.PAID)
        assertThat(actual.isPaid()).isTrue()
    }

    @Test
    fun `should throw exception if try to pay a paid bill`() = runBlocking {
        val bill = fakeBill.copy(paymentDate = LocalDate.now())
        bill.id = 100

        assertThat {
            createBill(bill)
            payBill(bill)
            payBill(bill)
        }.isFailure().isEqualTo(BillsIsAlreadyPaidException)
    }
}
