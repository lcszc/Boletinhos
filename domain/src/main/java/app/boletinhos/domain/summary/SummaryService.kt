package app.boletinhos.domain.summary

import kotlinx.coroutines.flow.Flow

interface SummaryService {
    suspend fun hasSummary(): Boolean
    fun getSummaries(): Flow<List<Summary>>
}
