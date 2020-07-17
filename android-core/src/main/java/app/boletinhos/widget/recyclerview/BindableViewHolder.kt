package app.boletinhos.widget.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BindableViewHolder<T> : RecyclerView.ViewHolder {
    constructor(view: View) : super(view)
    constructor(viewBinding: ViewBinding) : super(viewBinding.root)
    abstract fun bind(model: T)
}
