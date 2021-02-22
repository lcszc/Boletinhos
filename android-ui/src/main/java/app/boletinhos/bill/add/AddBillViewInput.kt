package app.boletinhos.bill.add

import java.time.LocalDate

data class AddBillViewInput(
    val value: Long,
    val name: String,
    val description: String,
    val dueDate: LocalDate?
)
