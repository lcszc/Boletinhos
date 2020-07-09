package app.boletinhos.decoration

import android.graphics.Rect
import android.view.View
import androidx.annotation.Dimension
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class ListMarginDecoration(
    @Dimension private val margin: Int
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val viewPosition = parent.getChildLayoutPosition(view)
        val layoutManager = parent.layoutManager

        val top = if(viewPosition == 0) margin else 0
        val bottom = margin

        var start = margin
        var end = margin

        if (layoutManager is StaggeredGridLayoutManager) {
            val viewSpanIndex = (view.layoutParams as StaggeredGridLayoutManager.LayoutParams)
                .spanIndex

            if (viewSpanIndex == 0 && viewPosition != 0) {
                end /= 2
            }

            if (viewSpanIndex == 1) {
                start /= 2
            }
        }

        outRect.set(start, top, end, bottom)
    }
}
