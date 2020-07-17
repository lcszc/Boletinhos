package app.boletinhos.widget.recyclerview

import androidx.recyclerview.widget.RecyclerView

abstract class ListAdapter<T> : RecyclerView.Adapter<BindableViewHolder<T>>() {
    var items: List<T> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: BindableViewHolder<T>, position: Int) {
        holder.bind(items[position])
    }
}
