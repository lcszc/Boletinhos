package app.boletinhos.summary.picker

import app.boletinhos.domain.summary.FetchSummary
import app.boletinhos.domain.summary.Summary
import app.boletinhos.domain.summary.SummaryService
import app.boletinhos.lifecycle.LifecycleAwareCoroutineScope
import com.zhuinden.simplestack.Bundleable
import com.zhuinden.simplestack.ScopedServices.Activated
import com.zhuinden.statebundle.StateBundle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class SummaryPickerViewModel @Inject constructor(
    private val coroutineScope: LifecycleAwareCoroutineScope,
    private val fetchSummaryUseCase: FetchSummary
) : Bundleable, CoroutineScope by coroutineScope {
    private val summariesState = fetchSummaryUseCase.summaries.combine(fetchSummaryUseCase()) { summaries, summary ->
        listOf(summary.asUiOption()) + summaries.map(Summary::asUiOption)
    }.

    override fun fromBundle(bundle: StateBundle?) {
        summariesState.value = bundle?.getParcelableArrayList(BUNDLE_KEY_SUMMARIES) ?: emptyList()
    }

    override fun toBundle(): StateBundle {
        return StateBundle().apply {
            putParcelableArrayList(BUNDLE_KEY_SUMMARIES, ArrayList(summariesState.value.orEmpty()))
        }
    }

    private companion object {
        private const val BUNDLE_KEY_SUMMARIES = "summaries"
    }
}
