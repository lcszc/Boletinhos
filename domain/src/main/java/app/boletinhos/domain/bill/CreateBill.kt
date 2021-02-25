package app.boletinhos.domain.bill

import javax.inject.Inject

class CreateBill @Inject constructor(
    private val gateway: BillGateway,
    private val validator: BillValidator
) {
    suspend operator fun invoke(bill: Bill) {
        validator.validate(bill)

        val status = if (bill.isOverdue()) BillStatus.OVERDUE else bill.status
        val newBill = bill.copy(status = status)

        newBill.id = bill.id
        gateway.create(newBill)
    }
}
