package app.boletinhos.domain.bill

class InMemoryBillGateway : BillGateway {
    private val _bills = mutableMapOf<Long, Bill>()
    val bills: Map<Long, Bill> = _bills

    override suspend fun create(bill: Bill) {
        if (bills.keys.contains(bill.id)) {
            throw IllegalStateException("Bill already exists.")
        }

        _bills += bill.id to bill
    }

    override suspend fun pay(bill: Bill) {
        if (!bills.keys.contains(bill.id)) {
            throw NullPointerException("This bill does not exists. We cant mark it as paid.")
        }

        _bills[bill.id] = bill
    }
}
