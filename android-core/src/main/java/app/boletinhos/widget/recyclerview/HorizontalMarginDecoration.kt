package app.boletinhos.widget.recyclerview

import android.graphics.Rect
import android.view.View
import androidx.annotation.Dimension
import androidx.recyclerview.widget.RecyclerView

open class HorizontalMarginDecoration(
    @Dimension private val margin: Int
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val viewPosition = parent.getChildLayoutPosition(view)

        val start = if(viewPosition == 0) 0 else margin
        val end = margin

        val top = 0
        val bottom = 0

        outRect.set(start, top, end, bottom)
    }
}
