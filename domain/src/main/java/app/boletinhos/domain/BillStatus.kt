package app.boletinhos.domain

enum class BillStatus(val code: StatusCode) {
    UNPAID(StatusCode(0)),
    PAID(StatusCode(1)),
    OVERDUE(StatusCode(2))
}