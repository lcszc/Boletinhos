package app.boletinhos.summary.picker

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import app.boletinhos.databinding.SummaryPickerOptionItemViewBinding
import app.boletinhos.ext.view.inflater
import app.boletinhos.widget.recyclerview.BindableViewHolder

class SummaryOptionAdapter(
    private val onItemClick: (itemId: Long) -> Unit
) : ListAdapter<SummaryOption, SummaryOptionViewHolder>(Diff) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SummaryOptionViewHolder {
        val binding = SummaryPickerOptionItemViewBinding.inflate(parent.inflater, parent, false)
        return SummaryOptionViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: SummaryOptionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private companion object Diff : DiffUtil.ItemCallback<SummaryOption>() {
        override fun areItemsTheSame(oldItem: SummaryOption, newItem: SummaryOption): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SummaryOption, newItem: SummaryOption): Boolean {
            return oldItem.isSelected == newItem.isSelected
        }
    }
}

class SummaryOptionViewHolder(
    private val viewBinding: SummaryPickerOptionItemViewBinding,
    private val onItemClick: (itemId: Long) -> Unit
) : BindableViewHolder<SummaryOption>(viewBinding) {
    override fun bind(model: SummaryOption) {
        viewBinding.root.setOnClickListener {
            onItemClick(model.id)
        }
        viewBinding.root.bindOption(model)
    }
}
