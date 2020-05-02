package app.boletinhos.core.bills

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface BillsDao {
    @Insert
    suspend fun insert(bill: Bill)

    @Update
    suspend fun update(bill: Bill)

    @Query("SELECT * FROM bills")
    fun getAll(): Flow<List<Bill>>

    @Query("SELECT * FROM bills WHERE status = :status")
    suspend fun getByStatus(status: BillStatus): List<Bill>
}