package app.boletinhos.error

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import app.boletinhos.databinding.ErrorViewBinding
import app.boletinhos.ext.view.inflater

class ErrorView(context: Context, attrs: AttributeSet? = null) : LinearLayout(context, attrs) {
    init {
        orientation = VERTICAL
        ErrorViewBinding.inflate(inflater, this)
    }

    private val viewBinding = ErrorViewBinding.bind(this)

    fun bindWith(model: ErrorViewModel, onTryAgain: () -> Unit) = viewBinding.run {
        textErrorTitle.setText(model.titleRes)
        textErrorMessage.setText(model.messageRes)
        actionTryAgain.setOnClickListener {
            onTryAgain()
        }
    }
}
