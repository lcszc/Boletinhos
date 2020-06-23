package app.boletinhos.bills

import androidx.room.Dao
import androidx.room.Query
import app.boletinhos.domain.bill.Bill
import app.boletinhos.domain.bill.BillStatus
import app.boletinhos.domain.bill.FetchBill
import kotlinx.coroutines.flow.Flow

@Dao internal interface InDatabaseFetchBillService : FetchBill {
    @Query("SELECT * FROM bills WHERE id = :id")
    override suspend fun getById(id: Long): Bill

    @Query("SELECT * FROM bills")
    override fun getAll(): Flow<List<Bill>>

    @Query("SELECT * FROM bills WHERE status = :status")
    override fun getByStatus(status: BillStatus): Flow<List<Bill>>
}
