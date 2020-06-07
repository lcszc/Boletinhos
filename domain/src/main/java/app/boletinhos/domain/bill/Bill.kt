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

    fun isOverdue() = dueDate.isBefore(LocalDate.now())

    fun hasValidMinimumValue() = value >= MINIMUM_VALUE

    fun hasValidMaximumValue() = value <= MAXIMUM_VALUE

    fun isNameTooShort() = name.length < MINIMUM_NAME_COUNT

    fun isNameTooLong() = name.length > MAXIMUM_NAME_COUNT

    fun isDescriptionTooShort() = description.length < MINIMUM_DESCRIPTION_COUNT

    fun isDescriptionTooLong() = description.length > MAXIMUM_DESCRIPTION_COUNT

    companion object {
        internal const val MINIMUM_VALUE = 10_00L /* 10 */

        internal const val MAXIMUM_VALUE = 250_000_00L /* 250k */

        internal const val MINIMUM_NAME_COUNT = 5

        internal const val MAXIMUM_NAME_COUNT = 48

        internal const val MINIMUM_DESCRIPTION_COUNT = 8

        internal const val MAXIMUM_DESCRIPTION_COUNT = 148
    }
}
