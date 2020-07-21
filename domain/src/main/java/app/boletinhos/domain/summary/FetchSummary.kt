package app.boletinhos.domain.summary

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class FetchSummary @Inject constructor(
    private val preferences: SummaryPreferences,
    private val service: SummaryService
) {
    operator fun invoke(): Flow<Summary> {
        return checkIfHasSummary().thenFetchSummaryIfTrue()
    }

    private fun checkIfHasSummary() = flow { emit(service.hasSummary()) }

    private fun Flow<Boolean>.thenFetchSummaryIfTrue(): Flow<Summary> {
        return flatMapLatest { hasSummary ->
            if (!hasSummary) emptyFlow()
            else fetchActualSummaryOrReturnEmpty()
        }
    }

    // one day: move this to service layer (fetchSummaryById -> Room/SQLDelight)
    private fun fetchActualSummaryOrReturnEmpty(): Flow<Summary> {
        val id = preferences.actualSummaryId()

        return service.getSummaries().flatMapLatest { summaries ->
            if (summaries.isEmpty()) return@flatMapLatest emptyFlow<Summary>()

            val summary = summaries
                .firstOrNull { summary -> summary.id() == id }
                ?: summaries.first()

            preferences.actualSummary(id = summary.id())
            flowOf(summary)
        }
    }
}
