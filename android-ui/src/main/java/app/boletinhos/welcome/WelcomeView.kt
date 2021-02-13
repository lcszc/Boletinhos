package app.boletinhos.welcome

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import app.boletinhos.databinding.WelcomeViewBinding
import app.boletinhos.ext.view.service

class WelcomeView(context: Context, attrs: AttributeSet? = null): LinearLayout(context, attrs) {
    private val binding by lazy { WelcomeViewBinding.bind(this) }
    private val viewModel by service<WelcomeViewModel>()

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        binding.actionAddBill.setOnClickListener {
            viewModel.onAddBillClick()
        }
    }
}
