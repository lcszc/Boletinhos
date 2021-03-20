package app.boletinhos.summary.picker

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import app.boletinhos.databinding.SummaryPickerBinding
import app.boletinhos.ext.view.service
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class SummaryPickerView(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {
    private val binding by lazy { SummaryPickerBinding.bind(this) }

    private val optionAdapter = SummaryOptionAdapter()
    private val viewModel by service<SummaryPickerViewModel>()

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        binding.summaries.adapter = optionAdapter

        viewModel.summaries.onEach {
            optionAdapter.submitList(it)
        }.launchIn(MainScope())
    }
}