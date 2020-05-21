package app.boletinhos.storage.typeconverter

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.Test
import java.time.LocalDate
import java.time.Month

class LocalDateTypeConverterTest {
    @Test fun `should parse to LocalDate from a given valid date`() {
        val expected = LocalDate.of(2020, Month.JANUARY, 30)

        val date = "2020-01-30"

        val actual = LocalDateTypeConverter.toLocalDateTime(date)

        assertThat(actual).isEqualTo(expected)
    }

    @Test fun `should format to text from a given LocalDate`() {
        val expected = "2020-01-30"

        val localDate = LocalDate.of(2020, Month.JANUARY, 30)

        val actual = LocalDateTypeConverter.fromLocalDateTime(localDate)

        assertThat(actual).isEqualTo(expected)
    }

    @Test fun `should convert to Month from a given integer`() {
        val expected = Month.DECEMBER

        val actual = LocalDateTypeConverter.toMonth(12.toString())

        assertThat(actual).isEqualTo(expected)
    }
}