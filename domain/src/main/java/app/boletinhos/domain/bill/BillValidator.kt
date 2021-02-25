package app.boletinhos.domain.bill

import app.boletinhos.domain.bill.error.BillValidationErrorType
import app.boletinhos.domain.bill.error.BillValidationException
import javax.inject.Inject

class BillValidator @Inject constructor() {
    fun validate(bill: Bill) = bill.run {
        val errors = mutableListOf<BillValidationErrorType?>()

        errors += validateValue()
        errors += validateName()
        errors += validateDescription()

        val outputErrors = errors.filterNotNull()
        if (outputErrors.isNotEmpty()) throw BillValidationException(errors = outputErrors)
    }

    private fun Bill.validateValue(): BillValidationErrorType? {
        val minValue = Bill.MINIMUM_VALUE
        val maxValue = Bill.MAXIMUM_VALUE

        val isValueValid = value in minValue..maxValue
        if (isValueValid) return null

        return if (value < minValue) {
            BillValidationErrorType.VALUE_MIN_REQUIRED
        } else BillValidationErrorType.VALUE_MAX_EXCEEDED
    }

    private fun Bill.validateName(): BillValidationErrorType? {
        val minCount = Bill.MINIMUM_NAME_COUNT
        val maxCount = Bill.MAXIMUM_NAME_COUNT

        val isNameValid = name.count() in minCount..maxCount
        if (isNameValid) return null

        return if (name.count() < minCount) {
            BillValidationErrorType.NAME_MIN_REQUIRED
        } else BillValidationErrorType.NAME_MAX_EXCEEDED
    }

    private fun Bill.validateDescription(): BillValidationErrorType? {
        val minCount = Bill.MINIMUM_DESCRIPTION_COUNT
        val maxCount = Bill.MAXIMUM_DESCRIPTION_COUNT

        val isDescriptionValid = description.count() in minCount..maxCount
        if (isDescriptionValid) return null

        return if (description.count() < minCount) {
            BillValidationErrorType.DESCRIPTION_MIN_REQUIRED
        } else BillValidationErrorType.DESCRIPTION_MAX_EXCEEDED
    }
}
