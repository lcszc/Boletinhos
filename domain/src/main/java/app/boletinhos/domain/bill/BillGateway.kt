package app.boletinhos.domain.bill

import app.boletinhos.domain.Bill

interface BillGateway {
    suspend fun createNewBill(bill: Bill)
}