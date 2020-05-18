package app.boletinhos.domain.bill

object FakeDBBillGateway : BillGateway {
    override suspend fun createNewBill(bill: Bill) = Unit
}