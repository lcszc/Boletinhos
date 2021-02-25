package app.boletinhos.domain.bill

import app.boletinhos.domain.bill.error.BillValidationErrorType
import app.boletinhos.domain.bill.error.BillValidationException
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFailure
import org.junit.Test
import java.time.LocalDate

class BillValidatorTest {
    private val validator = BillValidator()

    private val fakeBill = Bill(
        name = "Personal expense",
        description = "Food Category",
        value = 250_00L,
        paymentDate = null,
        dueDate = LocalDate.now(),
        status = BillStatus.UNPAID
    )

    @Test fun `should throw validation exception if min value required error`() {
        val bill = fakeBill.copy(value = 1_00L)

        val expectedError = BillValidationException(
            errors = listOf(BillValidationErrorType.VALUE_MIN_REQUIRED)
        )

        assertThat { validator.validate(bill) }.isFailure().isEqualTo(expectedError)
    }

    @Test fun `should throw validation exception if max value exceded error`() {
        val bill = fakeBill.copy(value = 9_000_000_00L)

        val expectedError = BillValidationException(
            errors = listOf(BillValidationErrorType.VALUE_MAX_EXCEEDED)
        )

        assertThat { validator.validate(bill) }.isFailure().isEqualTo(expectedError)
    }

    @Test fun `should throw validation exception if min name length required error`() {
        val bill = fakeBill.copy(name = "b")

        val expectedError = BillValidationException(
            errors = listOf(BillValidationErrorType.NAME_MIN_REQUIRED)
        )

        assertThat { validator.validate(bill) }.isFailure().isEqualTo(expectedError)
    }

    @Test fun `should throw validation exception if max name length exceeded error`() {
        val name = """
            Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor 
            incididunt ut labore et dolore magna aliqua. Ut diam quam nulla porttitor massa id neque. 
        """.trimIndent()

        val bill = fakeBill.copy(name = name)

        val expectedError = BillValidationException(
            errors = listOf(BillValidationErrorType.NAME_MAX_EXCEEDED)
        )

        assertThat { validator.validate(bill) }.isFailure().isEqualTo(expectedError)
    }

    @Test fun `should throw validation exception if min description length required error`() {
        val bill = fakeBill.copy(description = "d")

        val expectedError = BillValidationException(
            errors = listOf(BillValidationErrorType.DESCRIPTION_MIN_REQUIRED)
        )

        assertThat { validator.validate(bill) }.isFailure().isEqualTo(expectedError)
    }

    @Test fun `should throw validation exception if max description length exceeded error`() {
        val description = """
            Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor 
            incididunt ut labore et dolore magna aliqua. Ut diam quam nulla porttitor massa id neque. 
        """.trimIndent()

        val bill = fakeBill.copy(description = description)

        val expectedError = BillValidationException(
            errors = listOf(BillValidationErrorType.DESCRIPTION_MAX_EXCEEDED)
        )

        assertThat { validator.validate(bill) }.isFailure().isEqualTo(expectedError)
    }

    @Test fun `should throw exception with multiple errors`() {
        val bill = fakeBill.copy(name = "b", description = "a", value = 0)

        val expectedError = BillValidationException(
            errors = listOf(
                BillValidationErrorType.VALUE_MIN_REQUIRED,
                BillValidationErrorType.NAME_MIN_REQUIRED,
                BillValidationErrorType.DESCRIPTION_MIN_REQUIRED
            )
        )

        assertThat { validator.validate(bill) }.isFailure().isEqualTo(expectedError)
    }
}
