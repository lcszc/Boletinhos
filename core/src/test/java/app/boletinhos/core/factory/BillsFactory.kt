package app.boletinhos.core.factory

import app.boletinhos.domain.Bill
import app.boletinhos.domain.BillStatus
import java.time.LocalDate
import java.time.Month

object BillsFactory {
    val unpaid = Bill(
        1,
        name = "Unpaid bill",
        description = "unpaid",
        value = 200_00,
        paymentDate = null,
        dueDate = LocalDate.of(2020, Month.DECEMBER, 23),
        status = BillStatus.UNPAID
    )

    val paid = Bill(
        2,
        name = "paid bill",
        description = "paid",
        value = 200_00,
        paymentDate = LocalDate.of(2020, Month.DECEMBER, 20),
        dueDate = LocalDate.of(2020, Month.DECEMBER, 23),
        status = BillStatus.PAID
    )

    val overdue = Bill(
        3,
        name = "overdue bill",
        description = "overdue",
        value = 200_00,
        paymentDate = null,
        dueDate = LocalDate.of(2020, Month.DECEMBER, 1),
        status = BillStatus.OVERDUE
    )

    val merged = listOf(
        unpaid,
        unpaid.copy(id = 4, name = "New bill"),
        unpaid.copy(id = 5, name = "Another bill"),
        paid,
        paid.copy(id = 6, name = "2nd paid bill"),
        overdue
    )
}