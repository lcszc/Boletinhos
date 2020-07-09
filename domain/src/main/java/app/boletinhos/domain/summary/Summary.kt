package app.boletinhos.domain.summary

import java.time.Month

data class Summary(
    val month: Month,
    val year: Int,
    val totalValue: Long,
    val paids: Int,
    val unpaids: Int,
    val overdue: Int
) {
    /* one day: Move Summary to Room/SQLDelight */
    fun id(): Long = idFrom(month.value, year)

    companion object {
        fun idFrom(month: Int, year: Int): Long {
            return (month + year).toLong()
        }
    }
}