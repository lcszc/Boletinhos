package app.boletinhos.storage.bills

import app.boletinhos.domain.bill.Bill
import app.boletinhos.domain.bill.BillStatus.OVERDUE
import app.boletinhos.domain.bill.BillStatus.PAID
import app.boletinhos.domain.bill.BillStatus.UNPAID
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BillsRepository @Inject constructor(private val dao: BillsDao) {
    suspend fun new(bill: Bill) {
        val status = if (bill.isOverdue()) OVERDUE else UNPAID

        val newBill = bill
            .toEntity()
            .copy(status = status)

        dao.insert(newBill)
    }

    suspend fun pay(bill: Bill) {
        val paidBill = bill
            .toEntity()
            .copy(status = PAID, paymentDate = LocalDate.now())

        dao.update(paidBill)
    }
}