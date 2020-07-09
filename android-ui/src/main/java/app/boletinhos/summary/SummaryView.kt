package app.boletinhos.summary

import android.content.Context
import android.util.AttributeSet
import androidx.coordinatorlayout.widget.CoordinatorLayout
import app.boletinhos.databinding.SummaryViewBinding
import app.boletinhos.ext.view.service
import com.zhuinden.simplestackextensions.navigatorktx.backstack
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class SummaryView(
    context: Context,
    attrs: AttributeSet? = null
) : CoordinatorLayout(context, attrs) {
    private val viewBinding by lazy { SummaryViewBinding.bind(this) }

    private val viewEvents = BroadcastChannel<SummaryViewEvent>(Channel.BUFFERED)
    private val viewModel: SummaryViewModel = service()

    override fun onFinishInflate() {
        super.onFinishInflate()

        viewModel(viewEvents.asFlow()).renderOnUi()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        viewEvents.offer(SummaryViewEvent.OnAttach)
    }

    private fun Flow<SummaryViewState>.renderOnUi() {
        onEach {

        }.launchIn(viewScope)
    }
}
