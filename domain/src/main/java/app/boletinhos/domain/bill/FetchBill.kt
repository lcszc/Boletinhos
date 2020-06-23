package app.boletinhos.domain.bill

import kotlinx.coroutines.flow.Flow

interface FetchBill {
    suspend fun getById(id: Long): Bill
    fun getAll(): Flow<List<Bill>>
    fun getByStatus(status: BillStatus): Flow<List<Bill>>
}