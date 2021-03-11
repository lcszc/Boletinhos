package app.boletinhos.summary.picker

import app.boletinhos.domain.summary.FetchAndSelectSummary
import app.boletinhos.domain.summary.Summary
import app.boletinhos.domain.summary.SummaryPreferences
import app.boletinhos.domain.summary.SummaryService
import app.boletinhos.lifecycle.viewModelScope
import app.boletinhos.summary.SummaryViewKey
import app.boletinhos.summary.createSummaries
import assertk.assertThat
import assertk.assertions.containsExactly
import assertk.assertions.isEqualTo
import com.zhuinden.simplestack.Backstack
import com.zhuinden.simplestack.History
import com.zhuinden.simplestack.ScopedServices
import com.zhuinden.simplestackextensions.servicesktx.add
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.junit.Test

class SummaryPickerViewModelTest {
    private val backstack: Backstack = Backstack()

    private val summaries = createSummaries()
    private val selectedSummary = summaries.first()
    private val secondSelectedSummary = summaries.last()

    private val summaryPreferences: SummaryPreferences = mockk(relaxed = true) {
        coEvery { actualSummaryId() } returns selectedSummary.id()
    }
    private val summaryService: SummaryService = mockk(relaxed = true) {
        val summaries = createSummaries()

        coEvery { hasSummary() } returns true
        coEvery { getSummaries() } returns flowOf(summaries)
    }
    private val useCase = FetchAndSelectSummary(summaryPreferences, summaryService)

    private val viewKey = SummaryViewKey()
    private val scopeTag = viewKey.scopeTag

    private val viewModel: SummaryPickerViewModel = SummaryPickerViewModel(
        viewModelScope,
        useCase
    )

    private val scopedServices = ScopedServices { serviceBinder ->
        if (scopeTag == serviceBinder.scopeTag) {
            serviceBinder.add(viewModel)
        }
    }

    @Test fun `should emit user's actual selected summary`() {
        setUpBackStack()

        val selectedOption = selectedSummary.asUiOption().copy(isSelected = true)
        val items = ((summaries - selectedSummary).map(Summary::asUiOption) + selectedOption)
            .sortedByDescending(SummaryOption::isSelected)

        assertThat(viewModel.summaries.value).isEqualTo(items)
    }

    @Test fun `should update preferences when new summary selected`() {
        setUpBackStack()

        val selectedSummaryId = secondSelectedSummary.id()

        coEvery { summaryPreferences.actualSummaryId() } returns secondSelectedSummary.id()
        viewModel.onSummarySelected(selectedSummaryId)

        verify {
            useCase.select(id = selectedSummaryId)
            summaryPreferences.actualSummary(id = selectedSummaryId)
        }
    }

    @Test fun `should emit new selected summary`() {
        setUpBackStack()

        val selectedSummaryId = secondSelectedSummary.id()

        coEvery { summaryPreferences.actualSummaryId() } returns secondSelectedSummary.id()
        viewModel.onSummarySelected(selectedSummaryId)

        val selectedOption = secondSelectedSummary.asUiOption().copy(isSelected = true)
        val items = ((summaries - secondSelectedSummary).map(Summary::asUiOption) + selectedOption)
            .sortedByDescending(SummaryOption::isSelected)

        assertThat(viewModel.summaries.value).isEqualTo(items)
    }

    @Test fun `should emit loading states`() {
        val loadingStates = mutableListOf<Boolean>()
        viewModel.isLoading.onEach { loadingStates += it }.launchIn(viewModelScope)

        setUpBackStack()

        assertThat(loadingStates).containsExactly(
            false,
            true,
            false
        )
    }

    private fun setUpBackStack() {
        backstack.setScopedServices(scopedServices)
        backstack.setup(History.single(viewKey))
        backstack.setStateChanger { _, completionCallback ->
            completionCallback.stateChangeComplete()
        }
    }
}
