package app.boletinhos.domain.summary

import kotlinx.coroutines.flow.Flow

interface SummaryService {
    fun getSummary(): Flow<List<Summary>>
}