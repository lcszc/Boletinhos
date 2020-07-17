package app.boletinhos.summary

import android.graphics.Rect
import android.view.View
import androidx.annotation.Dimension
import androidx.recyclerview.widget.RecyclerView
import app.boletinhos.widget.recyclerview.VerticalMarginDecoration
import app.boletinhos.R.dimen as Dimens

class SummaryDecoration(
    @Dimension private val margin: Int
) : VerticalMarginDecoration(margin) {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val viewPosition = parent.getChildLayoutPosition(view)
        if (viewPosition == 0) return

        val isEven = viewPosition % 2 == 1

        outRect.left = if (isEven) outRect.left else outRect.left / 2
        outRect.right = if (isEven) outRect.right / 2 else outRect.right
    }
}

fun SummaryView.summaryDecoration() = SummaryDecoration(
    resources.getDimensionPixelSize(Dimens.app_margin_2x)
)
