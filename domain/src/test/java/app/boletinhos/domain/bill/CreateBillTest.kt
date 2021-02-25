package app.boletinhos.domain.bill

import app.boletinhos.domain.bill.error.BillValidationErrorType
import app.boletinhos.domain.bill.error.BillValidationException
import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.isEqualTo
import assertk.assertions.isFailure
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.time.LocalDate

class CreateBillTest {
    private val gateway = FakeBillGateway()
    private val validator = BillValidator()
    private val createBill = CreateBill(gateway, validator)

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

        createBill(bill)

        assertThat(gateway.bills).contains(key = 100, value = bill)
    }

    @Test fun `should throw bill has invalid value exception`() = runBlocking {
        val bill = fakeBill.copy(value = 1_00L)
        bill.id = 100

        val expectedError = BillValidationException(
            errors = listOf(BillValidationErrorType.VALUE_MIN_REQUIRED)
        )

        assertThat { createBill(bill) }.isFailure().isEqualTo(expectedError)
    }

    @Test fun `should throw bill has invalid name exception`() = runBlocking {
        val bill = fakeBill.copy(name = "P")
        bill.id = 100

        val expectedError = BillValidationException(
            errors = listOf(BillValidationErrorType.NAME_MIN_REQUIRED)
        )

        assertThat { createBill(bill) }.isFailure().isEqualTo(expectedError)
    }

    @Test fun `should throw bill has invalid description exception`() = runBlocking {
        val bill = fakeBill.copy(description = "F")
        bill.id = 100

        val expectedError = BillValidationException(
            errors = listOf(BillValidationErrorType.DESCRIPTION_MIN_REQUIRED)
        )

        assertThat { createBill(bill) }.isFailure().isEqualTo(expectedError)
    }
}
