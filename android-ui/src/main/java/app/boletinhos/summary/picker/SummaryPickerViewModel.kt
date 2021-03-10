package app.boletinhos.summary.picker

import app.boletinhos.domain.summary.FetchSummary
import app.boletinhos.domain.summary.Summary
import app.boletinhos.lifecycle.LifecycleAwareCoroutineScope
import com.zhuinden.simplestack.ScopedServices.Registered
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class SummaryPickerViewModel @Inject constructor(
    private val coroutineScope: LifecycleAwareCoroutineScope,
    private val fetchSummaryUseCase: FetchSummary
) : Registered, CoroutineScope by coroutineScope {
    private val summariesState = MutableStateFlow<List<SummaryOption>>(emptyList())
    val summaries = summariesState.asStateFlow()

    override fun onServiceRegistered() {
        fetchSummaries()
    }

    override fun onServiceUnregistered() = Unit

    fun onSummarySelected(id: Long) {
        fetchSummaryUseCase.select(id)
        fetchSummaries()
    }

    private fun fetchSummaries() {
        launch {
            fetchSummaryUseCase.summaries.combine(fetchSummaryUseCase()) { summaries, summary ->
                val currentSummary = summary.asUiOption()
                val currentSummaryAsSelected = currentSummary.copy(isSelected = true)
                summaries.map(Summary::asUiOption) - currentSummary + currentSummaryAsSelected
            }.map { options ->
                options.sortedByDescending(SummaryOption::isSelected)
            }.collect { options ->
                summariesState.value = options.sortedByDescending(SummaryOption::isSelected)
            }
        }
    }
}
