package app.boletinhos.core.typeconverter

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.Test
import org.threeten.bp.LocalDate
import org.threeten.bp.Month

class LocalDateTypeConverterTest {

    @Test fun `should parse to LocalDate from a given valid date`() {
        // @given a valid LocalDate
        val expected = LocalDate.of(2020, Month.JANUARY, 30)

        // @and a valid date
        val date = "30/1/2020"

        // @when parsing the date
        val actual = LocalDateTypeConverter.toLocalDateTime(date)

        // @then the result should be equal to the expected LocalDate
        assertThat(actual).isEqualTo(expected)
    }

    @Test fun `should format to text from a given LocalDate`() {
        // @given a valid date
        val expected = "30/1/2020"

        // @and a LocalDate
        val localDate = LocalDate.of(2020, Month.JANUARY, 30)

        // @when formatting the LocalDate to text
        val actual = LocalDateTypeConverter.fromLocalDateTime(localDate)

        // @then the result should be equal to the expected value
        assertThat(actual).isEqualTo(expected)
    }
}