package app.boletinhos.domain.currency

import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

object CurrencyMachine {
    private const val CURRENCY_BASE_VALUE = 100L

    fun formatFromRawValue(
        rawValue: Long,
        locale: Locale = Locale.getDefault()
    ): String {
        val numberFormatter = NumberFormat.getCurrencyInstance(locale)
        return numberFormatter.format(transformFromRawValueToBigDecimal(rawValue))
    }

    fun formatFromRawValueAndRemoveSymbol(
        rawValue: Long,
        locale: Locale = Locale.getDefault()
    ): String {
        val formatter = NumberFormat.getCurrencyInstance(locale) as DecimalFormat

        formatter.decimalFormatSymbols = formatter
            .decimalFormatSymbols.apply { currencySymbol = "" }

        return formatter.format(transformFromRawValueToBigDecimal(rawValue))
    }

    fun currencySymbol(locale: Locale): String {
        return numberFormatter(locale).currency?.symbol.orEmpty()
    }

    private fun numberFormatter(locale: Locale = Locale.getDefault()): NumberFormat {
        return NumberFormat.getCurrencyInstance(locale)
    }

    private fun transformFromRawValueToBigDecimal(rawValue: Long): BigDecimal {
        return BigDecimal(rawValue)
            .setScale(2, BigDecimal.ROUND_FLOOR)
            .divide(BigDecimal(CURRENCY_BASE_VALUE), BigDecimal.ROUND_FLOOR)
    }
}
