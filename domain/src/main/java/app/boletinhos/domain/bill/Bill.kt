package app.boletinhos.domain.bill

import app.boletinhos.domain.bill.status.BillStatus
import java.time.LocalDate

data class Bill(
    val id: Long,
    val name: String,
    val description: String,
    val value: Long,
    val paymentDate: LocalDate?,
    val dueDate: LocalDate,
    val status: BillStatus
) {
    val isOverdue = dueDate.isAfter(LocalDate.now())

    val hasValidMinimumValue get() = value >= MINIMUM_VALUE

    val hasValidMaximumValue get() = value <= MAXIMUM_VALUE

    val isNameTooShort get() = name.length < MINIMUM_NAME_COUNT

    val isNameTooLong get() = name.length > MAXIMUM_NAME_COUNT

    val isDescriptionTooShort get() = description.length < MINIMUM_DESCRIPTION_COUNT

    val isDescriptionTooLong get() = description.length > MAXIMUM_DESCRIPTION_COUNT

    companion object {
        internal const val MINIMUM_VALUE = 10_00L /* 10 */

        internal const val MAXIMUM_VALUE = 250_000_00L /* 250k */

        internal const val MINIMUM_NAME_COUNT = 5

        internal const val MAXIMUM_NAME_COUNT = 48

        internal const val MINIMUM_DESCRIPTION_COUNT = 8

        internal const val MAXIMUM_DESCRIPTION_COUNT = 148
    }
}