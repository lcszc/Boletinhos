package app.boletinhos.domain.bill

import app.boletinhos.domain.Bill

object FakeDBBillGateway : BillGateway {
    override suspend fun createNewBill(bill: Bill) = Unit
}