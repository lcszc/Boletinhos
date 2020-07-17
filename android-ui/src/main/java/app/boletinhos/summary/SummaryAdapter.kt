package app.boletinhos.summary

import android.view.ContextThemeWrapper
import android.view.ViewGroup
import app.boletinhos.summary.SummaryItemCardView.Model
import app.boletinhos.widget.recyclerview.BindableViewHolder
import app.boletinhos.widget.recyclerview.ListAdapter

class SummaryAdapter(
    private val onItemClick: (Model.Kind) -> Unit = {}
) : ListAdapter<Model>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val themeRes = Model.Kind.values()
            .first { it.viewType == viewType }
            .themeRes

        val view = SummaryItemCardView(ContextThemeWrapper(parent.context, themeRes))
        return ViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int = items[position].kind.viewType

    inner class ViewHolder(
        private val view: SummaryItemCardView
    ) : BindableViewHolder<Model>(view) {
        override fun bind(model: Model) {
            view.bind(model)

            view.setOnClickListener {
                onItemClick(model.kind)
            }
        }
    }
}
