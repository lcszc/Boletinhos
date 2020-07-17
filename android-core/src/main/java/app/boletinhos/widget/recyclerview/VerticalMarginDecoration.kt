package app.boletinhos.widget.recyclerview

import android.graphics.Rect
import android.view.View
import androidx.annotation.Dimension
import androidx.recyclerview.widget.RecyclerView

open class VerticalMarginDecoration(
    @Dimension private val margin: Int
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val viewPosition = parent.getChildLayoutPosition(view)

        val top = if(viewPosition == 0) margin else 0
        val bottom = margin

        val start = margin
        val end = margin

        outRect.set(start, top, end, bottom)
    }
}
