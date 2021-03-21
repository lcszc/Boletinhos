package app.boletinhos.summary.picker

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import app.boletinhos.R
import app.boletinhos.databinding.SummaryPickerBinding
import app.boletinhos.ext.view.service
import app.boletinhos.navigation.viewScope
import app.boletinhos.widget.recyclerview.HorizontalMarginDecoration
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class SummaryPickerView(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {
    private val binding by lazy { SummaryPickerBinding.bind(this) }

    private val optionAdapter = SummaryOptionAdapter {
        viewModel.onSummarySelected(it)
    }

    private val itemDecoration = HorizontalMarginDecoration(
        margin = resources.getDimensionPixelSize(R.dimen.app_margin_1x)
    )

    private val viewModel by service<SummaryPickerViewModel>()

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        configureViews()

        viewModel.summaries.onEach { summaries ->
            showSummaries(summaries)
        }.launchIn(viewScope)
    }

    private fun configureViews() {
        with(binding) {
            summaries.adapter = optionAdapter
            summaries.addItemDecoration(itemDecoration, 0)
            actionClose.setOnClickListener {
                viewModel.onCloseClicked()
            }
        }
    }

    private fun showSummaries(options: List<SummaryOption>) {
        optionAdapter.submitList(options)
    }
}