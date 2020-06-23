package app.boletinhos.domain.bill

import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.isEqualTo
import assertk.assertions.isFailure
import assertk.assertions.isTrue
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.time.LocalDate


class ManageBillTest {
    private val gateway = FakeManageBillService()
    private val useCase = ManageBill(gateway)

    private val fakeBill = Bill(
        name = "Personal expense",
        description = "Food Category",
        value = 250_00L,
        paymentDate = null,
        dueDate = LocalDate.now(),
        status = BillStatus.UNPAID
    )

    @Test fun `should create bill`() = runBlocking {
        val bill = fakeBill
        bill.id =  100

        useCase.createNew(bill)

        assertThat(gateway.bills).contains(key = 100, value = bill)
    }

    @Test fun `should throw bill has invalid value exception`() = runBlocking {
        val bill = fakeBill.copy(value = 1_00L)
        bill.id = 100

        assertThat {
            useCase.createNew(bill)
        }.isFailure().isEqualTo(BillHasInvalidValueException)
    }

    @Test fun `should throw bill has invalid name exception`() = runBlocking {
        val bill = fakeBill.copy(name = "P")
        bill.id = 100

        assertThat {
            useCase.createNew(bill)
        }.isFailure().isEqualTo(BillHasInvalidNameException)
    }

    @Test fun `should throw bill has invalid description exception`() = runBlocking {
        val bill = fakeBill.copy(description = "F")
        bill.id = 100

        assertThat {
            useCase.createNew(bill)
        }.isFailure().isEqualTo(BillHasInvalidDescriptionException)
    }

    @Test fun `should mark bill as paid`() = runBlocking {
        val bill = fakeBill
        bill.id = 100

        useCase.createNew(bill)
        useCase.markAsPaid(bill)

        val actual = gateway.bills.getValue(100)

        assertThat(actual.status).isEqualTo(BillStatus.PAID)
        assertThat(actual.isPaid()).isTrue()
    }

    @Test fun `should throw bill is already paid exception`() = runBlocking {
        val bill = fakeBill.copy(paymentDate = LocalDate.now())
        bill.id = 100

        assertThat {
            useCase.createNew(bill)
            useCase.markAsPaid(bill)
            useCase.markAsPaid(bill)
        }.isFailure().isEqualTo(BillsIsAlreadyPaidException)
    }
}
