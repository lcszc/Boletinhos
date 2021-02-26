package app.boletinhos.bill.add

import android.os.Parcelable
import android.view.View
import androidx.annotation.StringRes
import app.boletinhos.domain.bill.error.BillInvalidDueDateException
import app.boletinhos.domain.bill.error.BillValidationErrorType
import app.boletinhos.domain.bill.error.BillValidationErrorType.DESCRIPTION_MAX_EXCEEDED
import app.boletinhos.domain.bill.error.BillValidationErrorType.DESCRIPTION_MIN_REQUIRED
import app.boletinhos.domain.bill.error.BillValidationErrorType.NAME_MAX_EXCEEDED
import app.boletinhos.domain.bill.error.BillValidationErrorType.NAME_MIN_REQUIRED
import app.boletinhos.domain.bill.error.BillValidationErrorType.VALUE_MAX_EXCEEDED
import app.boletinhos.domain.bill.error.BillValidationErrorType.VALUE_MIN_REQUIRED
import app.boletinhos.domain.bill.error.BillValidationException
import app.boletinhos.domain.currency.CurrencyMachine
import kotlinx.android.parcel.Parcelize
import app.boletinhos.R.id as Ids
import app.boletinhos.R.string as Texts

private val BillValidationErrorType.fieldId: Int
    get() = when (this) {
        VALUE_MIN_REQUIRED, VALUE_MAX_EXCEEDED -> Ids.inputBillValue
        NAME_MIN_REQUIRED, NAME_MAX_EXCEEDED -> Ids.inputBillName
        DESCRIPTION_MIN_REQUIRED, DESCRIPTION_MAX_EXCEEDED -> Ids.inputBillDescription
    }

private val BillValidationErrorType.errorMessage: Int
    get() = when (this) {
        VALUE_MIN_REQUIRED -> Texts.message_bill_value_min_required
        VALUE_MAX_EXCEEDED -> Texts.message_bill_value_max_exceeded
        NAME_MIN_REQUIRED -> Texts.message_bill_name_min_length_required
        NAME_MAX_EXCEEDED -> Texts.message_bill_name_max_length_exceeded
        DESCRIPTION_MIN_REQUIRED -> Texts.message_bill_description_min_length_required
        DESCRIPTION_MAX_EXCEEDED -> Texts.message_bill_description_max_length_exceeded
    }

@Parcelize data class AddBillInputFieldError(
    @StringRes val messageRes: Int,
    val value: String? = null
) : Parcelable {
    companion object {
        operator fun invoke(error: BillValidationErrorType): AddBillInputFieldError {
            val value = if (error in VALUE_MIN_REQUIRED..VALUE_MAX_EXCEEDED) {
                CurrencyMachine.formatFromRawValue(error.rawValue)
            } else error.rawValue.toString()

            return AddBillInputFieldError(error.errorMessage, value)
        }
    }
}

@Parcelize data class AddBillViewError(
    val errors: Map<Int, AddBillInputFieldError>
) : Parcelable {
    companion object {
        operator fun invoke(error: Throwable): AddBillViewError {
            return when (error) {
                is BillInvalidDueDateException -> {
                    val fieldError = Ids.inputBillDueDate to AddBillInputFieldError(
                        messageRes = Texts.message_bill_invalid_due_date
                    )

                    AddBillViewError(errors = mapOf(fieldError))
                }

                is BillValidationException -> {
                    val fieldsErrors = error.errors
                        .map { errorType -> errorType.fieldId to AddBillInputFieldError(errorType) }
                        .associateBy({ it.first }, { it.second })

                    AddBillViewError(fieldsErrors)
                }

                else -> {
                    val fieldError = View.NO_ID to AddBillInputFieldError(
                        messageRes = Texts.message_bill_cannot_be_created
                    )

                    AddBillViewError(errors = mapOf(fieldError))
                }
            }
        }
    }
}
