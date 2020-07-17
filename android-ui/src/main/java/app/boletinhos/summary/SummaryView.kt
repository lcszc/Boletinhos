package app.boletinhos.summary

import android.content.Context
import android.util.AttributeSet
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.GridLayoutManager
import app.boletinhos.databinding.SummaryViewBinding

class SummaryView(
    context: Context,
    attrs: AttributeSet? = null
) : CoordinatorLayout(context, attrs) {
    private val viewBinding by lazy {
        SummaryViewBinding.bind(this)
    }

    private val viewDecoration = summaryDecoration()

    override fun onFinishInflate() {
        super.onFinishInflate()

    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

    }

    companion object{
        private val GRID_SPAN_LOOKUP = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position == 0) 2 else 1
            }
        }
    }
}
