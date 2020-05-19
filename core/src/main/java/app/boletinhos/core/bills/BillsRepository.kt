package app.boletinhos.core.bills

import app.boletinhos.domain.bill.Bill
import app.boletinhos.domain.bill.BillStatus.OVERDUE
import app.boletinhos.domain.bill.BillStatus.PAID
import app.boletinhos.domain.bill.BillStatus.UNPAID
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BillsRepository @Inject constructor(private val dao: BillsDao) {
    fun paids() = dao.getByStatus(PAID)

    fun overdue() = dao.getByStatus(OVERDUE)

    fun unpaids() = dao.getByStatus(UNPAID)

    suspend fun new(bill: Bill) {
        val status = if (bill.isOverdue) OVERDUE else UNPAID

        val newBill = bill
            .copy(status = status)
            .toEntity()

        dao.insert(newBill)
    }

    suspend fun pay(bill: Bill) {
        val paidBill = bill
            .copy(
                status = PAID,
                paymentDate = LocalDate.now()
            )
            .toEntity()

        dao.update(paidBill)
    }
}