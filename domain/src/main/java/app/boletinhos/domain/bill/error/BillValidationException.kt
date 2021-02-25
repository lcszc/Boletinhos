package app.boletinhos.domain.bill.error

data class BillValidationException(val errors: List<BillValidationErrorType>) : RuntimeException()
