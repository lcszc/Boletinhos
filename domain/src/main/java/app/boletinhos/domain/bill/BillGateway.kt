package app.boletinhos.domain.bill

interface BillGateway {
    suspend fun create(bill: Bill)
    suspend fun pay(bill: Bill)
}
