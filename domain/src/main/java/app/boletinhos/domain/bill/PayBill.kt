package app.boletinhos.domain.bill

import app.boletinhos.domain.bill.error.BillsIsAlreadyPaidException
import java.time.LocalDate
import javax.inject.Inject

class PayBill @Inject constructor(private val gateway: BillGateway) {
    suspend operator fun invoke(bill: Bill) {
        if (bill.isPaid()) throw BillsIsAlreadyPaidException

        val billToPay = bill.copy(
            status = BillStatus.PAID,
            paymentDate = LocalDate.now()
        )

        billToPay.id = bill.id
        gateway.pay(billToPay)
    }
}
