package app.boletinhos.widget.text

import android.content.Context
import android.util.AttributeSet
import app.boletinhos.core.databinding.TextInputContentBinding
import app.boletinhos.ext.view.inflater
import com.google.android.material.textfield.TextInputLayout

open class TextInput(
    context: Context,
    attrs: AttributeSet? = null
) : TextInputLayout(context, attrs) {
    internal lateinit var inputBinding: TextInputContentBinding
        private set

    open var value: String
        get() = inputBinding.input.text?.toString().orEmpty()
        set(value) = setTextInternal(value)

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        inputBinding = TextInputContentBinding.inflate(inflater, this, true)
    }

    private fun setTextInternal(value: String) {
        inputBinding.input.setText(value)
    }
}
