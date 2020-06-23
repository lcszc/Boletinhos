package app.boletinhos.domain.bill

interface ManageBillService {
    suspend fun create(bill: Bill)
    suspend fun pay(bill: Bill)
}
