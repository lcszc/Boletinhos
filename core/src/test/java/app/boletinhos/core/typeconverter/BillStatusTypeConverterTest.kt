package app.boletinhos.core.typeconverter

import app.boletinhos.domain.bill.status.BillStatus
import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.Test

class BillStatusTypeConverterTest {
    @Test fun `should convert to status from a given integer`() {
        // @given a bill status
        val expected = BillStatus.UNPAID

        // @and its expected status code value
        val code = BillStatus.UNPAID.code.value

        // @when converting to a status from a given code
        val actual = BillStatusTypeConverter.toStatus(code)

        // @then the converted result should be `UNPAID`
        assertThat(actual).isEqualTo(expected)
    }

    @Test fun `should convert to int from a given status`() {
        // @given a bill status code value
        val expected = BillStatus.UNPAID.code.value

        // @and its bill status
        val status = BillStatus.UNPAID

        // @when converting to a integer from a given status
        val actual = BillStatusTypeConverter.fromStatus(status)

        // @then the converted result should be `0`
        assertThat(actual).isEqualTo(expected)
    }
}