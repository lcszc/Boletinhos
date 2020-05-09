package app.boletinhos.core.bills

import app.boletinhos.domain.Bill

fun BillEntity.toDomain() = Bill(
    uid,
    name,
    description,
    value,
    paymentDate,
    dueDate,
    status
)