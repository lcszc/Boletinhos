package app.boletinhos.domain.bill

import javax.inject.Inject

class CreateBill @Inject constructor(private val gateway: BillGateway) {
    suspend operator fun invoke(bill: Bill) {
        val newBill = when {
            !bill.isValueValid -> throw BillHasInvalidValueException
            !bill.isNameValid -> throw BillHasInvalidNameException
            !bill.isDescriptionValid -> throw BillHasInvalidDescriptionException
            else -> {
                val status = if (bill.isOverdue()) BillStatus.OVERDUE else bill.status
                bill.copy(status = status)
            }
        }

        newBill.id = bill.id
        gateway.create(newBill)
    }
}
