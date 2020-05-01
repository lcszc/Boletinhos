package app.boletinhos.core.bills

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.TypeConverters
import androidx.room.Update
import app.boletinhos.core.typeconverter.LocalDateTypeConverter
import kotlinx.coroutines.flow.Flow

@Dao
@TypeConverters(value = [LocalDateTypeConverter::class])
interface BillsDao {
    @Insert
    suspend fun insert(bill: Bill)

    @Update
    suspend fun update(bill: Bill)

    @Query("SELECT * FROM bills")
    fun getAll(): Flow<Bill>
}