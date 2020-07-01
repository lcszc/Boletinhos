package app.boletinhos.summary

import app.boletinhos.domain.summary.SummaryService
import common.ActivityRetainedScope
import common.UiCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@ActivityRetainedScope
class SummaryViewModel @Inject constructor(
    @UiCoroutineScope
    coroutineScope: CoroutineScope,
    service: SummaryService
) {
    init {
        coroutineScope.launch {
            service.getSummary()
                .onStart { _state.value = SummaryViewState(isLoading = true) }
                .onEmpty { _state.value = SummaryViewState(summary = null) }
                .collect { _state.value = SummaryViewState(summary = it.first()) }
        }
    }

    private val _state: MutableStateFlow<SummaryViewState> = MutableStateFlow(SummaryViewState())
    val state: StateFlow<SummaryViewState> = _state
}
