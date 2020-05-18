package app.boletinhos.domain.bill

import app.boletinhos.domain.bill.status.BillStatus
import java.time.LocalDate

object BillFactory {
    fun get() = Bill(
        id = 0,
        name = "House's Electricity",
        description = "Electricity",
        value = 250_00L,
        dueDate = LocalDate.now(),
        paymentDate = null,
        status = BillStatus.UNPAID
    )
}
