package app.boletinhos.domain.bill

import java.time.LocalDate

data class Bill(
    val name: String,
    val description: String,
    val value: Long,
    val paymentDate: LocalDate?,
    val dueDate: LocalDate,
    val status: BillStatus
) {
    var id: Long = 0L

    fun isOverdue(): Boolean {
        if (isPaid()) return false
        return dueDate.isBefore(LocalDate.now())
    }

    fun isPaid() = paymentDate != null

    companion object {
        const val MINIMUM_VALUE = 10_00L /* 10 */
        const val MAXIMUM_VALUE = 250_000_00L /* 250k */
        const val MINIMUM_NAME_COUNT = 5
        const val MAXIMUM_NAME_COUNT = 48
        const val MINIMUM_DESCRIPTION_COUNT = 8
        const val MAXIMUM_DESCRIPTION_COUNT = 148
    }
}
