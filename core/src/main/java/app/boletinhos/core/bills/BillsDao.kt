package app.boletinhos.core.bills

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import app.boletinhos.domain.Bill
import app.boletinhos.domain.BillStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface BillsDao {
    @Insert
    suspend fun insert(bill: BillEntity)

    @Update
    suspend fun update(bill: BillEntity)

    @Query("SELECT * FROM bills")
    fun getAll(): Flow<List<Bill>>

    @Query("SELECT * FROM bills WHERE status = :status")
    suspend fun getByStatus(status: BillStatus): List<Bill>
}