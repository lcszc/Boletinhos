package app.boletinhos.summary.picker

import android.content.Context
import android.util.AttributeSet
import app.boletinhos.databinding.SummaryPickerOptionItemViewBinding
import com.google.android.material.card.MaterialCardView

class SummaryPickerItemView(
    context: Context,
    attrs: AttributeSet? = null
) : MaterialCardView(context, attrs) {
    private val binding by lazy {
        SummaryPickerOptionItemViewBinding.bind(this)
    }

    fun bindOption(option: SummaryOption) {
        isSelected = option.isSelected

        binding.textSummary.isSelected = option.isSelected
        binding.textSummary.text = option.toString()
    }
}
