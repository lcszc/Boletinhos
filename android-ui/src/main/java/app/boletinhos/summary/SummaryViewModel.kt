package app.boletinhos.summary

import app.boletinhos.bill.add.AddBillViewKey
import app.boletinhos.domain.summary.FetchSummary
import app.boletinhos.domain.summary.Summary
import app.boletinhos.error.ErrorViewModel
import app.boletinhos.lifecycle.LifecycleAwareCoroutineScope
import app.boletinhos.summary.SummaryItemCardView.Model.Kind
import app.boletinhos.welcome.WelcomeViewKey
import com.zhuinden.simplestack.Backstack
import com.zhuinden.simplestack.Bundleable
import com.zhuinden.simplestack.History
import com.zhuinden.simplestack.StateChange
import com.zhuinden.statebundle.StateBundle
import common.ActivityRetainedScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onEmpty
import javax.inject.Inject
import app.boletinhos.R.drawable as Drawables
import app.boletinhos.R.string as Texts

@ActivityRetainedScope
class SummaryViewModel @Inject constructor(
    private val viewModelScope: LifecycleAwareCoroutineScope,
    private val fetchSummary: FetchSummary,
    private val backstack: Backstack
) : Bundleable {
    private val viewState = MutableStateFlow(SummaryViewState())

    override fun fromBundle(bundle: StateBundle?) {
        viewState.value = bundle?.getParcelable(KEY_BUNDLE_SUMMARY_STATE) ?: SummaryViewState()
    }

    override fun toBundle(): StateBundle {
        return StateBundle().putParcelable(KEY_BUNDLE_SUMMARY_STATE, viewState.value)
    }

    operator fun invoke(viewEvents: Flow<SummaryViewEvent>): Flow<SummaryViewState> {
        viewEvents.filterIsInstance<SummaryViewEvent.FetchData>().thenFetch()
        viewEvents.filterIsInstance<SummaryViewEvent.OnClickInAddBill>().navigate()
        return viewState
    }

    private fun Flow<SummaryViewEvent.FetchData>.thenFetch() {
        onEach {
            viewState.value = SummaryViewState(isLoading = true)
            fetchSummary()
                .flowOn(viewModelScope.io)
                .ifSuccessRenderOnUi()
                .onErrorRenderOnUi()
                .ifEmptyLaunchWelcomeScreen()
                .launchIn(viewModelScope)
        }.launchIn(viewModelScope)
    }

    private fun Flow<SummaryViewEvent.OnClickInAddBill>.navigate() {
        onEach { backstack.goTo(AddBillViewKey()) }.launchIn(viewModelScope)
    }

    private fun Flow<Summary>.ifEmptyLaunchWelcomeScreen() = onEmpty {
        backstack.setHistory(
            History.of(WelcomeViewKey()),
            StateChange.BACKWARD
        )
    }

    private fun Flow<Summary>.ifSuccessRenderOnUi() = onEach { summary ->
        val itemMonth = SummaryItemCardView.Model(
            iconRes = Drawables.ic_summary,
            titleRes = Texts.text_month_summary,
            titleArg = summary.displayName(),
            descriptionRes = Texts.text_bills,
            textValue = summary.formattedTotalValue(),
            kind = Kind.MONTH_SUMMARY
        )

        val itemPaids = SummaryItemCardView.Model(
            iconRes = Drawables.ic_paid,
            titleRes = Texts.text_bills_paids,
            descriptionRes = Texts.text_bills,
            textValue = summary.paids.toString(),
            kind = Kind.PAIDS
        )

        val itemUnpaids = SummaryItemCardView.Model(
            iconRes = Drawables.ic_hourglass,
            titleRes = Texts.text_bills_unpaids,
            descriptionRes = Texts.text_bills,
            textValue = summary.unpaids.toString(),
            kind = Kind.UNPAIDS
        )

        val itemOverdue = SummaryItemCardView.Model(
            iconRes = Drawables.ic_calendar,
            titleRes = Texts.text_bills_overdue,
            descriptionRes = Texts.text_bills,
            textValue = summary.overdue.toString(),
            kind = Kind.OVERDUE
        )

        viewState.value = SummaryViewState(
            isActionAndSummaryVisible = true,
            summary = listOf(itemMonth, itemPaids, itemUnpaids, itemOverdue)
        )
    }

    private fun Flow<Summary>.onErrorRenderOnUi() = catch {
        val error = ErrorViewModel(
            titleRes = Texts.text_summary_error_title,
            messageRes = Texts.text_summary_error_message
        )

        viewState.value = SummaryViewState(error = error)
    }

    companion object {
        internal const val KEY_BUNDLE_SUMMARY_STATE = "boletinhos.summaryviewstate.bundle"
    }
}
