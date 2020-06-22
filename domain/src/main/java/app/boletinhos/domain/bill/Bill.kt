package app.boletinhos.domain.bill

import java.time.LocalDate

object BillsIsAlreadyPaidException : IllegalStateException()
object BillHasInvalidValueException : IllegalArgumentException()
object BillHasInvalidNameException : IllegalArgumentException()
object BillHasInvalidDescriptionException : IllegalArgumentException()

data class Bill(
    val name: String,
    val description: String,
    val value: Long,
    val paymentDate: LocalDate?,
    val dueDate: LocalDate,
    val status: BillStatus
) {
    var id: Long = 0L

    fun isOverdue() = dueDate.isBefore(LocalDate.now())

    fun isPaid() = paymentDate != null

    val isValueValid get() = value in MINIMUM_VALUE..MAXIMUM_VALUE

    val isNameValid get() = name.count() in MINIMUM_NAME_COUNT..MAXIMUM_NAME_COUNT

    val isDescriptionValid get() = description.count() in MINIMUM_DESCRIPTION_COUNT..MAXIMUM_DESCRIPTION_COUNT

    companion object {
        internal const val MINIMUM_VALUE = 10_00L /* 10 */

        internal const val MAXIMUM_VALUE = 250_000_00L /* 250k */

        internal const val MINIMUM_NAME_COUNT = 5

        internal const val MAXIMUM_NAME_COUNT = 48

        internal const val MINIMUM_DESCRIPTION_COUNT = 8

        internal const val MAXIMUM_DESCRIPTION_COUNT = 148
    }
}
