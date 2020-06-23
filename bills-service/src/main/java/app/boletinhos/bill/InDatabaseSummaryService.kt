package app.boletinhos.bill

import androidx.room.Dao
import androidx.room.Query
import app.boletinhos.domain.summary.SummaryService
import app.boletinhos.domain.summary.Summary
import kotlinx.coroutines.flow.Flow

@Dao internal interface InDatabaseSummaryService : SummaryService {
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
    override fun getSummary(): Flow<List<Summary>>
}
