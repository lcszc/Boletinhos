package app.boletinhos.domain.summary

import java.io.Serializable
import java.text.NumberFormat
import java.time.Month
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

data class Summary(
    val month: Month,
    val year: Int,
    val totalValue: Long,
    val paids: Int,
    val unpaids: Int,
    val overdue: Int
) : Serializable {
    /* one day: Move Summary to Room/SQLDelight */
    fun id(): Long = idFrom(month.value, year)

    fun displayName(locale: Locale = Locale.getDefault()): String {
        return YearMonth.of(year, month)
            .format(DateTimeFormatter.ofPattern("MMMM uuuu", locale))
    }

    fun formattedTotalValue(locale: Locale = Locale.getDefault()): String {
        return NumberFormat.getCurrencyInstance(locale)
            .format(totalValue / CURRENCY_DIVIDER)
    }

    companion object {
        private const val CURRENCY_DIVIDER = 10

        fun idFrom(month: Int, year: Int): Long {
            return (month + year).toLong()
        }
    }
}
