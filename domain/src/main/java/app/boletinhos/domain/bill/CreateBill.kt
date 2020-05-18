package app.boletinhos.domain.bill

class CreateBill(private val gateway: BillGateway) {
    suspend operator fun invoke(bill: Bill) = when {
        bill.hasValidMinimumValue.not() -> throw InvalidMinimumBillValueException()
        bill.hasValidMaximumValue.not() -> throw InvalidMaximumBillValueException()
        bill.isNameTooShort -> throw BillNameTooShortException()
        bill.isNameTooLong -> throw BillNameTooLongException()
        bill.isDescriptionTooShort -> throw BillDescriptionTooShortException()
        bill.isDescriptionTooLong -> throw BillDescriptionTooLongException()
        else -> gateway.createNewBill(bill)
    }
}