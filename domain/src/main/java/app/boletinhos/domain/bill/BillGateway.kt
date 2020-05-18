package app.boletinhos.domain.bill

interface BillGateway {
    suspend fun createNewBill(bill: Bill)
}