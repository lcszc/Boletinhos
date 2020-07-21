package app.boletinhos.summary

import android.content.Context
import android.util.AttributeSet
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import app.boletinhos.databinding.SummaryViewBinding
import app.boletinhos.databinding.SummaryViewErrorBinding
import app.boletinhos.error.ErrorViewModel
import app.boletinhos.ext.view.service
import app.boletinhos.navigation.viewScope
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class SummaryView(
    context: Context,
    attrs: AttributeSet? = null
) : CoordinatorLayout(context, attrs) {
    private val viewBinding by lazy {
        SummaryViewBinding.bind(this)
    }

    private val errorView by lazy {
        SummaryViewErrorBinding.bind(viewBinding.stubError.inflate()).error
    }

    private val viewModel by service<SummaryViewModel>()
    private val viewEvents = BroadcastChannel<SummaryViewEvent>(
        if (isInEditMode) CHANNEL_CAPACITY_EDIT_MODE else Channel.BUFFERED
    )

    private val summaryAdapter = SummaryAdapter()
    private val summaryDecoration = summaryDecoration()

    override fun onFinishInflate() {
        super.onFinishInflate()
        viewBinding.summary.run {
            adapter = summaryAdapter
            (layoutManager as GridLayoutManager).spanSizeLookup = GRID_SPAN_LOOKUP

            setHasFixedSize(true)
            addItemDecoration(summaryDecoration)
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (isInEditMode) return

        viewModel(viewEvents.asFlow()).renderViewState()

        viewScope.launch {
            viewEvents.send(SummaryViewEvent.FetchData)
        }
    }

    private fun Flow<SummaryViewState>.renderViewState() = viewBinding.run {
        onEach { state ->
            showOrHideLoadingState(
                isLoading = state.isLoading,
                showFields = state.isActionAndSummaryVisible
            )

            state.summary?.showOnUi() ?: state.error?.showErrorOnUi()
        }.launchIn(viewScope)
    }

    private fun showOrHideLoadingState(isLoading: Boolean, showFields: Boolean) = viewBinding.run {
        sequenceOf(summary, actionAddBill).forEach { view ->
            view.isVisible = showFields
        }

        progressLoading.isVisible = isLoading
    }

    private fun List<SummaryItemCardView.Model>.showOnUi() {
        summaryAdapter.items = this
    }

    private fun ErrorViewModel.showErrorOnUi() {
        errorView.bindWith(model = this) {
            viewEvents.offer(SummaryViewEvent.FetchData)
        }
    }

    companion object{
        private val GRID_SPAN_LOOKUP = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position == 0) 2 else 1
            }
        }

        const val CHANNEL_CAPACITY_EDIT_MODE = 1
    }
}
