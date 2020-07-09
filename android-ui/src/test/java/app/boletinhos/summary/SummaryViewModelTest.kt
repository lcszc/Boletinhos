package app.boletinhos.summary

import app.boletinhos.R

import app.boletinhos.domain.summary.FetchSummary
import app.boletinhos.domain.summary.Summary
import app.boletinhos.domain.summary.SummaryPreferences
import app.boletinhos.domain.summary.SummaryService
import app.boletinhos.lifecycle.viewModelScope
import app.boletinhos.wip.WipViewKey
import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.containsExactly
import assertk.assertions.hasSize
import assertk.assertions.isEqualTo
import com.zhuinden.simplestack.Backstack
import com.zhuinden.simplestack.History
import com.zhuinden.simplestack.navigator.DefaultViewKey
import com.zhuinden.simplestackextensions.services.DefaultServiceProvider
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import testutil.MainCoroutineRule
import java.time.Month
import java.util.Locale

class SummaryViewModelTest {
    @get:Rule val mainCoroutineRule = MainCoroutineRule()

    private val summaryPreferences: SummaryPreferences = mockk(relaxed = true)
    private val summaryService: SummaryService = mockk(relaxed = true)
    private lateinit var backstack: Backstack

    private val useCase by lazy { FetchSummary(summaryPreferences, summaryService) }
    private val viewModel by lazy { SummaryViewModel(viewModelScope, useCase, backstack) }

    private val viewEvents = BroadcastChannel<SummaryViewEvent>(Channel.BUFFERED)

    @Before fun setUp() {
        backstack = Backstack().apply {
            setScopedServices(DefaultServiceProvider())
            setup(History.of(WipViewKey("Summary Screen")))
            setStateChanger { _, completionCallback -> completionCallback.stateChangeComplete() }
        }
    }

    @Test fun `should navigate backward to welcome screen if there is no summary available`() {
        coEvery { summaryService.hasSummary() } returns false

        viewModel(viewEvents.asFlow())
        viewEvents.offer(SummaryViewEvent.OnAttach)

        assertThat(backstack.top<DefaultViewKey>()).isEqualTo(WipViewKey("Welcome Screen!"))
        assertThat(backstack.getHistory<DefaultViewKey>()).hasSize(1)
    }

    @Test fun `should emit user's actual selected summary`() {
        val summary1 = Summary(month = Month.JANUARY, year = 2019, totalValue = 1900, paids = 0, unpaids = 0, overdue = 0)
        val summary2 = summary1.copy(month = Month.FEBRUARY)

        coEvery { summaryService.hasSummary() } returns true
        coEvery { summaryPreferences.actualSummaryId() } returns summary1.id()
        coEvery { summaryService.getSummaries() } returns flowOf(listOf(summary1, summary2))

        val states = mutableListOf<SummaryViewState>()

        viewModel(viewEvents.asFlow()).onEach { state ->
            states.add(state)
        }.launchIn(viewModelScope)

        viewEvents.offer(SummaryViewEvent.OnAttach)

        val locale = Locale.getDefault()

        val items = listOf(
            SummaryItemCardView.Model(
                iconRes = R.drawable.ic_summary,
                titleRes = R.string.text_month_summary,
                titleArg = summary1.displayName(locale),
                descriptionRes = R.string.text_bills,
                textValue = summary1.totalValue.toString(),
                kind = SummaryItemCardView.Model.Kind.MONTH_SUMMARY
            ),
            SummaryItemCardView.Model(
                iconRes = R.drawable.ic_check,
                titleRes = R.string.text_bills_paids,
                descriptionRes = R.string.text_bills,
                textValue = summary1.paids.toString(),
                kind = SummaryItemCardView.Model.Kind.PAIDS
            ),

            SummaryItemCardView.Model(
                iconRes = R.drawable.ic_hourglass,
                titleRes = R.string.text_bills_unpaids,
                descriptionRes = R.string.text_bills,
                textValue = summary1.unpaids.toString(),
                kind = SummaryItemCardView.Model.Kind.UNPAIDS
            ),

            SummaryItemCardView.Model(
                iconRes = R.drawable.ic_check,
                titleRes = R.string.text_bills_overdue,
                descriptionRes = R.string.text_bills,
                textValue = summary1.overdue.toString(),
                kind = SummaryItemCardView.Model.Kind.OVERDUE
            )
        )

        assertThat(states).contains(SummaryViewState(summary = items))
    }

    @Test fun `should emit most recent summary if user didn't selected any`() {
        val summary1 = Summary(month = Month.JANUARY, year = 2019, totalValue = 1900, paids = 0, unpaids = 0, overdue = 0)
        val summary2 = summary1.copy(month = Month.FEBRUARY)

        coEvery { summaryService.hasSummary() } returns true
        coEvery { summaryPreferences.actualSummaryId() } returns SummaryPreferences.NO_SUMMARY
        coEvery { summaryService.getSummaries() } returns flowOf(listOf(summary2, summary1))

        val states = mutableListOf<SummaryViewState>()

        viewModel(viewEvents.asFlow()).onEach { state ->
            states.add(state)
        }.launchIn(viewModelScope)

        viewEvents.offer(SummaryViewEvent.OnAttach)

        val locale = Locale.getDefault()

        val items = listOf(
            SummaryItemCardView.Model(
                iconRes = R.drawable.ic_summary,
                titleRes = R.string.text_month_summary,
                titleArg = summary2.displayName(locale),
                descriptionRes = R.string.text_bills,
                textValue = summary2.totalValue.toString(),
                kind = SummaryItemCardView.Model.Kind.MONTH_SUMMARY
            ),
            SummaryItemCardView.Model(
                iconRes = R.drawable.ic_check,
                titleRes = R.string.text_bills_paids,
                descriptionRes = R.string.text_bills,
                textValue = summary2.paids.toString(),
                kind = SummaryItemCardView.Model.Kind.PAIDS
            ),

            SummaryItemCardView.Model(
                iconRes = R.drawable.ic_hourglass,
                titleRes = R.string.text_bills_unpaids,
                descriptionRes = R.string.text_bills,
                textValue = summary2.unpaids.toString(),
                kind = SummaryItemCardView.Model.Kind.UNPAIDS
            ),

            SummaryItemCardView.Model(
                iconRes = R.drawable.ic_check,
                titleRes = R.string.text_bills_overdue,
                descriptionRes = R.string.text_bills,
                textValue = summary2.overdue.toString(),
                kind = SummaryItemCardView.Model.Kind.OVERDUE
            )
        )

        assertThat(states).contains(SummaryViewState(summary = items))
    }

    @Test fun `should emit null summary if fetching throws any error`() {
        coEvery { summaryService.hasSummary() } returns true
        coEvery { summaryPreferences.actualSummaryId() } returns SummaryPreferences.NO_SUMMARY
        coEvery { summaryService.getSummaries() } throws IllegalStateException("god knows why")

        val states = mutableListOf<SummaryViewState>()

        viewModel(viewEvents.asFlow()).onEach { state ->
            states.add(state)
        }.launchIn(viewModelScope)

        viewEvents.offer(SummaryViewEvent.OnAttach)

        assertThat(states.last()).isEqualTo(SummaryViewState(summary = null))
    }

    @Test fun `should emit loading process`() {
        val summary1 = Summary(month = Month.JANUARY, year = 2019, totalValue = 1900, paids = 0, unpaids = 0, overdue = 0)
        val summary2 = summary1.copy(month = Month.FEBRUARY)

        coEvery { summaryService.hasSummary() } returns true
        coEvery { summaryPreferences.actualSummaryId() } returns SummaryPreferences.NO_SUMMARY
        coEvery { summaryService.getSummaries() } returns flowOf(listOf(summary2, summary1))

        val states = mutableListOf<SummaryViewState>()

        viewModel(viewEvents.asFlow()).onEach { state ->
            states.add(state)
        }.launchIn(viewModelScope)

        viewEvents.offer(SummaryViewEvent.OnAttach)

        val locale = Locale.getDefault()

        val items = listOf(
            SummaryItemCardView.Model(
                iconRes = R.drawable.ic_summary,
                titleRes = R.string.text_month_summary,
                titleArg = summary2.displayName(locale),
                descriptionRes = R.string.text_bills,
                textValue = summary2.totalValue.toString(),
                kind = SummaryItemCardView.Model.Kind.MONTH_SUMMARY
            ),
            SummaryItemCardView.Model(
                iconRes = R.drawable.ic_check,
                titleRes = R.string.text_bills_paids,
                descriptionRes = R.string.text_bills,
                textValue = summary2.paids.toString(),
                kind = SummaryItemCardView.Model.Kind.PAIDS
            ),

            SummaryItemCardView.Model(
                iconRes = R.drawable.ic_hourglass,
                titleRes = R.string.text_bills_unpaids,
                descriptionRes = R.string.text_bills,
                textValue = summary2.unpaids.toString(),
                kind = SummaryItemCardView.Model.Kind.UNPAIDS
            ),

            SummaryItemCardView.Model(
                iconRes = R.drawable.ic_check,
                titleRes = R.string.text_bills_overdue,
                descriptionRes = R.string.text_bills,
                textValue = summary2.overdue.toString(),
                kind = SummaryItemCardView.Model.Kind.OVERDUE
            )
        )


        assertThat(states).containsExactly(
            SummaryViewState(), // <- default
            SummaryViewState(isLoading = true),
            SummaryViewState(summary = items)
        )
    }
}
