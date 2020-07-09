package app.boletinhos.domain.summary

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeSummaryService(private val db: List<Summary>) : SummaryService {
    override suspend fun hasSummary(): Boolean {
        return db.isNotEmpty()
    }

    override fun getSummaries(): Flow<List<Summary>> {
        return flowOf(db)
    }
}