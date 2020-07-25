package app.boletinhos.summary

import app.boletinhos.domain.summary.FetchSummary
import app.boletinhos.domain.summary.SummaryPreferences
import app.boletinhos.domain.summary.SummaryService
import app.boletinhos.error.ErrorViewModel
import app.boletinhos.lifecycle.viewModelScope
import app.boletinhos.testutil.TestKey
import app.boletinhos.wip.WipViewKey
import assertk.assertThat
import assertk.assertions.*
import com.zhuinden.simplestack.Backstack
import com.zhuinden.simplestack.History
import com.zhuinden.simplestack.ServiceBinder
import com.zhuinden.simplestack.navigator.DefaultViewKey
import com.zhuinden.simplestackextensions.services.DefaultServiceProvider
import com.zhuinden.simplestackextensions.servicesktx.add
import com.zhuinden.simplestackextensions.servicesktx.lookup
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
import app.boletinhos.R.string as Texts

class SummaryViewModelTest {
    @get:Rule val mainCoroutineRule = MainCoroutineRule()

    private val summaryPreferences: SummaryPreferences = mockk(relaxed = true)
    private val summaryService: SummaryService = mockk(relaxed = true)
    private val useCase = FetchSummary(summaryPreferences, summaryService)
    private val viewEvents = BroadcastChannel<SummaryViewEvent>(Channel.BUFFERED)

    private lateinit var backstack: Backstack

    @Before fun setUp() {
        backstack = Backstack().apply {
            setScopedServices(DefaultServiceProvider())
            setup(History.of(TestSummaryViewKey()))
            setStateChanger { _, completionCallback -> completionCallback.stateChangeComplete() }
        }
    }

    @Test fun `should navigate backward to welcome screen if there is no summary available`() {
        coEvery { summaryService.hasSummary() } returns false

        val viewModel = backstack.lookup<SummaryViewModel>()
        viewModel(viewEvents.asFlow())

        viewEvents.offer(SummaryViewEvent.FetchData)

        assertThat(backstack.top<DefaultViewKey>()).isEqualTo(WipViewKey("Welcome Screen"))
        assertThat(backstack.getHistory<DefaultViewKey>()).hasSize(1)
    }

    @Test fun `should emit user's actual selected summary`() {
        val summaries = createSummaries()
        val selectedSummary = summaries.first()

        coEvery { summaryService.hasSummary() } returns true
        coEvery { summaryPreferences.actualSummaryId() } returns selectedSummary.id()
        coEvery { summaryService.getSummaries() } returns flowOf(summaries)

        val states = mutableListOf<SummaryViewState>()
        val viewModel = backstack.lookup<SummaryViewModel>()
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
        val viewModel = backstack.lookup<SummaryViewModel>()
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

        val viewModel = backstack.lookup<SummaryViewModel>()
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
        val viewModel = backstack.lookup<SummaryViewModel>()
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
        val viewModel = backstack.lookup<SummaryViewModel>()
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
        val viewModel = backstack.lookup<SummaryViewModel>()
        viewModel(viewEvents.asFlow())
            .onEach { state -> states.add(state) }
            .launchIn(viewModelScope)

        viewEvents.offer(SummaryViewEvent.FetchData)

        // -> State Restoration
        val stateBundle = backstack.toBundle()

        val newBackstack = Backstack().apply {
            setScopedServices(DefaultServiceProvider())
            setup(History.of(SummaryViewKey()))
            fromBundle(stateBundle)
            setStateChanger { _, completionCallback -> completionCallback.stateChangeComplete() }
        }

        val viewModel2 = newBackstack.lookup<SummaryViewModel>()
        viewModel2(viewEvents.asFlow())
            .onEach { restoredState -> assertThat(restoredState).isEqualTo(states.last()) }
            .launchIn(viewModelScope)
    }

    inner class TestSummaryViewKey : TestKey(), DefaultServiceProvider.HasServices {
        override fun bindServices(serviceBinder: ServiceBinder) {
            val backstack = serviceBinder.backstack
            serviceBinder.add(SummaryViewModel(viewModelScope, useCase, backstack))
        }

        override fun getScopeTag() = this::class.java.name
    }
}
