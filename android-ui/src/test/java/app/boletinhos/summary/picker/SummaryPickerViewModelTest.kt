package app.boletinhos.summary.picker

import app.boletinhos.domain.summary.FetchSummary
import app.boletinhos.domain.summary.Summary
import app.boletinhos.domain.summary.SummaryPreferences
import app.boletinhos.domain.summary.SummaryService
import app.boletinhos.lifecycle.viewModelScope
import app.boletinhos.summary.SummaryViewKey
import app.boletinhos.summary.createSummaries
import assertk.assertThat
import assertk.assertions.isEqualTo
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
        coEvery { actualSummaryId() } returns selectedSummary.id()
    }
    private val summaryService: SummaryService = mockk(relaxed = true) {
        val summaries = createSummaries()

        coEvery { hasSummary() } returns true
        coEvery { getSummaries() } returns flowOf(summaries)
    }
    private val useCase = FetchSummary(summaryPreferences, summaryService)

    private val viewKey = SummaryViewKey()
    private val scopeTag = viewKey.scopeTag

    private lateinit var viewModel: SummaryPickerViewModel

    private val scopedServices = ScopedServices { serviceBinder ->
        if (scopeTag == serviceBinder.scopeTag) {
            viewModel = SummaryPickerViewModel(viewModelScope, useCase)
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

    @Test fun `should emit user's actual selected summary`() {
        val selectedOption = selectedSummary.asUiOption().copy(isSelected = true)
        val items = ((summaries - selectedSummary).map(Summary::asUiOption) + selectedOption)
            .sortedByDescending(SummaryOption::isSelected)

        assertThat(viewModel.summaries.value).isEqualTo(items)
    }

    @Test fun `should update preferences when new summary selected`() {
        val selectedSummaryId = secondSelectedSummary.id()

        coEvery { summaryPreferences.actualSummaryId() } returns secondSelectedSummary.id()
        viewModel.onSummarySelected(selectedSummaryId)

        verify {
            useCase.select(id = selectedSummaryId)
            summaryPreferences.actualSummary(id = selectedSummaryId)
        }
    }

    @Test fun `should emit new selected summary`() {
        val selectedSummaryId = secondSelectedSummary.id()

        coEvery { summaryPreferences.actualSummaryId() } returns secondSelectedSummary.id()
        viewModel.onSummarySelected(selectedSummaryId)

        val selectedOption = secondSelectedSummary.asUiOption().copy(isSelected = true)
        val items = ((summaries - secondSelectedSummary).map(Summary::asUiOption) + selectedOption)
            .sortedByDescending(SummaryOption::isSelected)

        assertThat(viewModel.summaries.value).isEqualTo(items)
    }
}