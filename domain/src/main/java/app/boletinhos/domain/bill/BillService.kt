package app.boletinhos.domain.bill

import kotlinx.coroutines.flow.Flow

interface BillService {
    suspend fun getById(id: Long): Bill
    fun getAll(): Flow<List<Bill>>
    fun getByStatus(status: BillStatus): Flow<List<Bill>>
}
