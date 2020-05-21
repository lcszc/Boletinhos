package app.boletinhos.core.bills

import androidx.room.Dao
import androidx.room.Query
import app.boletinhos.domain.bill.Summary
import kotlinx.coroutines.flow.Flow

@Dao
interface BillsSummaryDao {
    @Query(
        """
        SELECT
            strftime('%m', dueDate) AS month, 
            strftime('%Y', dueDate) AS year, 
            SUM(value) AS totalValue, 
            SUM(status = 'PAID') AS paids, 
            SUM(status = 'UNPAID') AS unpaids,
            SUM(status = 'OVERDUE') AS overdue 
        FROM bills 
        GROUP BY strftime('%m-%Y', dueDate)
        ORDER BY dueDate DESC
    """
    )
    fun getSummary(): Flow<List<Summary>>
}