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

fun Bill.toEntity() = BillEntity(
    name,
    description,
    value,
    paymentDate,
    dueDate,
    status
).also { entity -> entity.uid = id }