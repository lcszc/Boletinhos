package app.boletinhos.core.bills

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import app.boletinhos.domain.bill.Bill
import app.boletinhos.domain.bill.BillStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface BillsDao {
    @Insert
    suspend fun insert(bill: BillEntity)

    @Update
    suspend fun update(bill: BillEntity)

    @Query("SELECT * FROM bills WHERE id = :id")
    suspend fun getById(id: Long): Bill

    @Query("SELECT * FROM bills")
    fun getAll(): Flow<List<Bill>>

    @Query("SELECT * FROM bills WHERE status = :status")
    fun getByStatus(status: BillStatus): Flow<List<Bill>>
}