package app.boletinhos.testutil

import app.boletinhos.domain.bill.Bill
import app.boletinhos.domain.bill.BillStatus
import app.boletinhos.domain.bill.ManageBillService
import app.boletinhos.domain.summary.Summary
import kotlinx.coroutines.runBlocking
import java.time.LocalDate
import javax.inject.Inject

class FakeBillsFactory @Inject constructor(private val manageBillService: ManageBillService) {
    fun createFakeSummary(): Summary {
        val bill = Bill(
            name = "Some Bill name",
            description = "Some bill description",
            value = 99_90,
            paymentDate = null,
            dueDate = LocalDate.now(),
            status = BillStatus.UNPAID
        )

        val bill2 = bill.copy(value = 50_00)
        val bill3 = bill.copy(value = 150_00)
        val bill4 = bill.copy(paymentDate = LocalDate.now(), status = BillStatus.PAID)

        val bills = listOf(bill, bill2, bill3, bill4)

        runBlocking {
            bills.forEach { manageBillService.create(it) }
        }

        return Summary(
            month = LocalDate.now().month,
            year = LocalDate.now().year,
            totalValue = bills.fold(0L) { totalValue, actualBill -> totalValue + actualBill.value },
            paids = bills.count { it.status == BillStatus.PAID },
            unpaids = bills.count { it.status == BillStatus.UNPAID },
            overdue = bills.count { it.status == BillStatus.OVERDUE }
        )
    }
}
