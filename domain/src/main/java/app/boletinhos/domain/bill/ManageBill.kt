package app.boletinhos.domain.bill

import java.time.LocalDate

class ManageBill(private val gateway: ManageBillService) {
    suspend fun createNew(bill: Bill) {
        val newBill = when {
            !bill.isValueValid -> throw BillHasInvalidValueException
            !bill.isNameValid -> throw BillHasInvalidNameException
            !bill.isDescriptionValid -> throw BillHasInvalidDescriptionException
            else -> {
                val status = if (bill.isOverdue()) BillStatus.OVERDUE else BillStatus.UNPAID
                bill.copy(status = status)
            }
        }

        newBill.id = bill.id
        gateway.create(newBill)
    }

    suspend fun markAsPaid(bill: Bill) {
        if (bill.isPaid()) {
            throw BillsIsAlreadyPaidException
        }

        val billToPay = bill.copy(
            status = BillStatus.PAID,
            paymentDate = LocalDate.now()
        )

        billToPay.id = bill.id
        gateway.pay(billToPay)
    }
}