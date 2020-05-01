package app.boletinhos.core.factory

import app.boletinhos.core.bills.Bill
import org.threeten.bp.LocalDate
import org.threeten.bp.Month

object BillsFactory {
    val unpaid = Bill(
        name = "Unpaid bill",
        description = "unpaid",
        value = 200_00,
        paymentDate = null,
        dueDate = LocalDate.of(2020, Month.DECEMBER, 23)
    )

    val paid = Bill(
        name = "paid bill",
        description = "paid",
        value = 200_00,
        paymentDate = LocalDate.of(2020, Month.DECEMBER, 20),
        dueDate = LocalDate.of(2020, Month.DECEMBER, 23)
    )

    val overdue = Bill(
        name = "overdue bill",
        description = "overdue",
        value = 200_00,
        paymentDate = null,
        dueDate = LocalDate.of(2020, Month.DECEMBER, 1)
    )
}