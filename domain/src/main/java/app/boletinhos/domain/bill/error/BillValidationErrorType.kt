package app.boletinhos.domain.bill.error

import app.boletinhos.domain.bill.Bill.Companion.MAXIMUM_DESCRIPTION_COUNT
import app.boletinhos.domain.bill.Bill.Companion.MAXIMUM_NAME_COUNT
import app.boletinhos.domain.bill.Bill.Companion.MAXIMUM_VALUE
import app.boletinhos.domain.bill.Bill.Companion.MINIMUM_DESCRIPTION_COUNT
import app.boletinhos.domain.bill.Bill.Companion.MINIMUM_NAME_COUNT
import app.boletinhos.domain.bill.Bill.Companion.MINIMUM_VALUE

enum class BillValidationErrorType(val rawValue: Long) {
    VALUE_MIN_REQUIRED(rawValue = MINIMUM_VALUE),
    VALUE_MAX_EXCEEDED(rawValue = MAXIMUM_VALUE),
    NAME_MIN_REQUIRED(rawValue = MINIMUM_NAME_COUNT.toLong()),
    NAME_MAX_EXCEEDED(rawValue = MAXIMUM_NAME_COUNT.toLong()),
    DESCRIPTION_MIN_REQUIRED(rawValue = MINIMUM_DESCRIPTION_COUNT.toLong()),
    DESCRIPTION_MAX_EXCEEDED(rawValue = MAXIMUM_DESCRIPTION_COUNT.toLong())
}
