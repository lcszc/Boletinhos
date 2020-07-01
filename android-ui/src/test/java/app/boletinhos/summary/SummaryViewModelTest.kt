package app.boletinhos.summary

import app.boletinhos.domain.summary.Summary
import app.boletinhos.domain.summary.SummaryService
import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class SummaryViewModelTest {
    private val coroutineScope: CoroutineScope = TestCoroutineScope()
    private lateinit var viewModel: SummaryViewModel

    private val fakeEmptySummaryService = object : SummaryService {
        override fun getSummary(): Flow<List<Summary>> {
            return emptyFlow()
        }
    }

    @Test fun `should summary be null if there are no summaries found`() = runBlockingTest {
        viewModel = SummaryViewModel(coroutineScope, fakeEmptySummaryService)
        assertThat(viewModel.state.value).isEqualTo(SummaryViewState(isLoading = false, summary = null))
    }
}
