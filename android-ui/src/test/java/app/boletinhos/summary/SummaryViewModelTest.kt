package app.boletinhos.summary

import app.boletinhos.domain.summary.FetchAndSelectSummary
import app.boletinhos.domain.summary.SummaryPreferences
import app.boletinhos.domain.summary.SummaryService
import app.boletinhos.error.ErrorViewModel
import app.boletinhos.lifecycle.viewModelScope
import app.boletinhos.welcome.WelcomeViewKey
import assertk.assertThat
import assertk.assertions.*
import com.zhuinden.simplestack.Backstack
import com.zhuinden.simplestack.History
import com.zhuinden.simplestack.ScopedServices
import com.zhuinden.simplestack.navigator.DefaultViewKey
import com.zhuinden.simplestackextensions.servicesktx.add
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import testutil.MainCoroutineRule
import app.boletinhos.R.string as Texts

class SummaryViewModelTest {
    @get:Rule val mainCoroutineRule = MainCoroutineRule()

    private val summaryPreferences: SummaryPreferences = mockk(relaxed = true)
    private val summaryService: SummaryService = mockk(relaxed = true)
    private val useCase = FetchAndSelectSummary(summaryPreferences, summaryService)
    private val viewEvents = BroadcastChannel<SummaryViewEvent>(Channel.BUFFERED)

    private val backstack: Backstack = Backstack()

    private val viewKey = SummaryViewKey()
    private val scopeTag = viewKey.scopeTag

    private lateinit var viewModel: SummaryViewModel

    private val scopedServices = ScopedServices { serviceBinder ->
        if (scopeTag == serviceBinder.scopeTag) {
            viewModel = SummaryViewModel(viewModelScope, useCase, serviceBinder.backstack)
            serviceBinder.add(viewModel)
        }
    }

    @Before fun setUp() {
        backstack.setScopedServices(scopedServices)
        backstack.setup(History.single(viewKey))
        backstack.setStateChanger { _, completionCallback ->
            completionCallback.stateChangeComplete()
        }
    }

    @Test fun `should navigate backward to welcome screen if there is no summary available`() {
        coEvery { summaryService.hasSummary() } returns false

        viewModel(viewEvents.asFlow())
        viewEvents.offer(SummaryViewEvent.FetchData)

        assertThat(backstack.top<DefaultViewKey>()).isInstanceOf(WelcomeViewKey::class)
        assertThat(backstack.getHistory<DefaultViewKey>()).hasSize(1)
    }

    @Test fun `should emit user's actual selected summary`() {
        val summaries = createSummaries()
        val selectedSummary = summaries.first()

        coEvery { summaryService.hasSummary() } returns true
        coEvery { summaryPreferences.actualSummaryId() } returns selectedSummary.id()
        coEvery { summaryService.getSummaries() } returns flowOf(summaries)

        val states = mutableListOf<SummaryViewState>()

        viewModel(viewEvents.asFlow())
            .onEach { state -> states.add(state) }
            .launchIn(viewModelScope)

        viewEvents.offer(SummaryViewEvent.FetchData)

        val items = createItemsFromSummary(selectedSummary)

        assertThat(states).contains(
            SummaryViewState(isActionAndSummaryVisible = true, summary = items)
        )
    }

    @Test fun `should emit most recent summary if user didn't selected any`() {
        val summaries = createSummaries().sortedByDescending { it.id() }
        val mostRecentSummary = summaries.first()

        coEvery { summaryService.hasSummary() } returns true
        coEvery { summaryPreferences.actualSummaryId() } returns SummaryPreferences.NO_SUMMARY
        coEvery { summaryService.getSummaries() } returns flowOf(summaries)

        val states = mutableListOf<SummaryViewState>()
        viewModel(viewEvents.asFlow())
            .onEach { state -> states.add(state) }
            .launchIn(viewModelScope)

        viewEvents.offer(SummaryViewEvent.FetchData)

        val items = createItemsFromSummary(mostRecentSummary)

        assertThat(states).contains(
            SummaryViewState(isActionAndSummaryVisible = true, summary = items)
        )
    }

    @Test fun `should emit null summary if fetching throws any error`() {
        coEvery { summaryService.hasSummary() } returns true
        coEvery { summaryPreferences.actualSummaryId() } returns SummaryPreferences.NO_SUMMARY
        coEvery { summaryService.getSummaries() } throws IllegalStateException("god knows why")

        val states = mutableListOf<SummaryViewState>()

        viewModel(viewEvents.asFlow()).onEach { state ->
            states.add(state)
        }.launchIn(viewModelScope)

        viewEvents.offer(SummaryViewEvent.FetchData)

        assertThat(states.last().summary).isNull()
    }

    @Test fun `should emit error if summary has not found due to some exception`() {
        coEvery { summaryService.hasSummary() } returns true
        coEvery { summaryPreferences.actualSummaryId() } returns SummaryPreferences.NO_SUMMARY
        coEvery { summaryService.getSummaries() } throws IllegalStateException("god knows why")

        val states = mutableListOf<SummaryViewState>()
        viewModel(viewEvents.asFlow())
            .onEach { state -> states.add(state) }
            .launchIn(viewModelScope)

        viewEvents.offer(SummaryViewEvent.FetchData)

        val error = ErrorViewModel(
            titleRes = Texts.text_summary_error_title,
            messageRes = Texts.text_summary_error_message
        )

        assertThat(states.last()).isEqualTo(SummaryViewState(error = error))
    }

    @Test fun `should emit loading process`() {
        val summaries = createSummaries().sortedByDescending { it.id() }
        val mostRecentSummary = summaries.first()

        coEvery { summaryService.hasSummary() } returns true
        coEvery { summaryPreferences.actualSummaryId() } returns SummaryPreferences.NO_SUMMARY
        coEvery { summaryService.getSummaries() } returns flowOf(summaries)

        val states = mutableListOf<SummaryViewState>()
        viewModel(viewEvents.asFlow())
            .onEach { state -> states.add(state) }
            .launchIn(viewModelScope)

        viewEvents.offer(SummaryViewEvent.FetchData)

        val items = createItemsFromSummary(mostRecentSummary)

        assertThat(states).containsExactly(
            SummaryViewState(), // <- default
            SummaryViewState(isLoading = true),
            SummaryViewState(isActionAndSummaryVisible = true, summary = items)
        )
    }

    @Test fun `should persist and restore view state`() {
        val summaries = createSummaries()

        coEvery { summaryService.hasSummary() } returns true
        coEvery { summaryPreferences.actualSummaryId() } returns SummaryPreferences.NO_SUMMARY
        coEvery { summaryService.getSummaries() } returns flowOf(summaries)

        val states = mutableListOf<SummaryViewState>()
        viewModel(viewEvents.asFlow())
            .onEach { state -> states.add(state) }
            .launchIn(viewModelScope)

        viewEvents.offer(SummaryViewEvent.FetchData)

        // -> State Restoration
        val newBackstack = Backstack()
        newBackstack.setScopedServices(scopedServices)
        newBackstack.setup(History.single(viewKey))
        newBackstack.fromBundle(backstack.toBundle())
        newBackstack.setStateChanger { _, completionCallback ->
            completionCallback.stateChangeComplete()
        }

        val viewModel2 = newBackstack.getService<SummaryViewModel>(
            scopeTag,
            SummaryViewModel::class.java.name
        )

        viewModelScope.launch {
            val restoredState = viewModel2(viewEvents.asFlow()).first()
            assertThat(restoredState).isEqualTo(states.last())
        }
    }
}
