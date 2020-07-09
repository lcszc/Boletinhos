package app.boletinhos.summary

import android.view.ContextThemeWrapper
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager.LayoutParams.WRAP_CONTENT
import app.boletinhos.summary.SummaryItemCardView.Model
import app.boletinhos.summary.SummaryViewListAdapter.ViewHolder


class SummaryViewListAdapter(
    private val onItemClick: (Model.Kind) -> Unit = {}
) : RecyclerView.Adapter<ViewHolder>() {
    val dataSource: MutableList<Model> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val themeRes = Model.Kind.values()
            .first { it.viewType == viewType }
            .themeRes

        val view = SummaryItemCardView(ContextThemeWrapper(parent.context, themeRes))
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = dataSource.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val viewModel = dataSource[position]

        if (viewModel.kind.isFullSpan) {
            val newLayoutParams = StaggeredGridLayoutManager
                .LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
                .apply { isFullSpan = true }

            holder.itemView.layoutParams = newLayoutParams
        }

        holder.bind(dataSource[position])
    }

    override fun getItemViewType(position: Int): Int = dataSource[position].kind.viewType

    inner class ViewHolder(
        private val view: SummaryItemCardView
    ) : RecyclerView.ViewHolder(view) {
        fun bind(model: Model) {
            view.bind(model)

            view.setOnClickListener {
                onItemClick(model.kind)
            }
        }
    }
}
