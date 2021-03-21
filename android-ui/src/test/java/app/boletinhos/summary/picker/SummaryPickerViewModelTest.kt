package app.boletinhos.summary.picker

import app.boletinhos.domain.summary.FetchAndSelectSummary
import app.boletinhos.domain.summary.Summary
import app.boletinhos.domain.summary.SummaryPreferences
import app.boletinhos.domain.summary.SummaryService
import app.boletinhos.lifecycle.viewModelScope
import app.boletinhos.navigation.ViewKey
import app.boletinhos.summary.SummaryViewKey
import app.boletinhos.summary.createSummaries
import app.boletinhos.summary.picker.SummaryPickerViewModel.OnSummarySelectionChange
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import com.zhuinden.simplestack.Backstack
import com.zhuinden.simplestack.History
import com.zhuinden.simplestack.ScopedServices
import com.zhuinden.simplestackextensions.servicesktx.add
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Test

class SummaryPickerViewModelTest {
    private val backstack: Backstack = Backstack()

    private val summaries = createSummaries()
    private val selectedSummary = summaries.first()
    private val secondSelectedSummary = summaries.last()

    private val summaryPreferences: SummaryPreferences = mockk(relaxed = true) {
        coEvery { summaryId() } returns selectedSummary.id()
    }

    private val summaryService: SummaryService = mockk(relaxed = true) {
        val summaries = createSummaries()

        coEvery { hasSummary() } returns true
        coEvery { getSummaries() } returns flowOf(summaries)
    }

    private val useCase = FetchAndSelectSummary(summaryPreferences, summaryService)

    private val summarySelectionChangeHandler = mockk<OnSummarySelectionChange>(relaxed = true)

    private val viewKey = SummaryViewKey()
    private val scopeTag = viewKey.scopeTag

    private val viewModel: SummaryPickerViewModel = SummaryPickerViewModel(
        viewModelScope,
        useCase,
        summarySelectionChangeHandler,
        backstack
    )

    private val scopedServices = ScopedServices { serviceBinder ->
        if (scopeTag == serviceBinder.scopeTag) {
            serviceBinder.add(viewModel)
        }
    }

    @Before fun setUp() {
        setUpBackStack()
        backstack.goTo(SummaryPickerViewKey())
    }

    @Test fun `should emit user's selected summary`() {
        val selectedOption = selectedSummary.asUiOption().copy(isSelected = true)
        val items = ((summaries - selectedSummary).map(Summary::asUiOption) + selectedOption)
            .sortedByDescending(SummaryOption::isSelected)

        assertThat(viewModel.summaries.value).isEqualTo(items)
    }

    @Test fun `should update preferences when new summary selected`() {
        val selectedSummaryId = secondSelectedSummary.id()

        coEvery { summaryPreferences.summaryId() } returns secondSelectedSummary.id()
        viewModel.onSummarySelected(selectedSummaryId)

        verify {
            useCase.select(id = selectedSummaryId)
            summaryPreferences.summaryId(id = selectedSummaryId)
        }
    }

    @Test fun `should notify summary has been selected`() {
        val selectedSummaryId = secondSelectedSummary.id()

        coEvery { summaryPreferences.summaryId() } returns secondSelectedSummary.id()
        viewModel.onSummarySelected(selectedSummaryId)

        verify {
            summarySelectionChangeHandler.onSummarySelected()
        }
    }

    @Test fun `should go back after selecting new summary`() {
        val selectedSummaryId = secondSelectedSummary.id()

        coEvery { summaryPreferences.summaryId() } returns secondSelectedSummary.id()
        viewModel.onSummarySelected(selectedSummaryId)

        assertThat(backstack.top<ViewKey>()).isInstanceOf(SummaryViewKey::class)
    }

    @Test fun `should go back after clicking on close`() {
        viewModel.onCloseClicked()
        assertThat(backstack.top<ViewKey>()).isInstanceOf(SummaryViewKey::class)
    }

    private fun setUpBackStack() {
        backstack.setScopedServices(scopedServices)
        backstack.setup(History.single(viewKey))
        backstack.setStateChanger { _, completionCallback ->
            completionCallback.stateChangeComplete()
        }
    }
}
