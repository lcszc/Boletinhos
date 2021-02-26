package app.boletinhos.domain.currency

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.Test
import java.util.Locale

class CurrencyMachineTest {
    @Test fun `should format raw value and keep currency symbol`() {
        val expected = "$250,000.00"
        val rawValue = 250_000_00L
        val actual = CurrencyMachine.formatFromRawValue(rawValue = rawValue, locale = Locale.US)

        assertThat(actual).isEqualTo(expected)
    }

    @Test fun `should format from raw value and remove currency symbol`() {
        val expected = "250.000,00" /* ptbr */
        val rawValue = 250_000_00L
        val actual = CurrencyMachine.formatFromRawValueAndRemoveSymbol(rawValue = rawValue, locale = Locale("pt", "Br"))
        assertThat(actual.trim()).isEqualTo(expected)
    }
}
