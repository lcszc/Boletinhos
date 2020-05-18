package app.boletinhos.core.bills

import app.boletinhos.domain.bill.Bill

fun Bill.toEntity() = BillEntity(
    id,
    name,
    description,
    value,
    paymentDate,
    dueDate,
    status
)