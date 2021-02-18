package app.boletinhos.widget.currency

import android.content.Context
import android.text.InputFilter.LengthFilter
import android.text.InputType
import android.util.AttributeSet
import app.boletinhos.widget.text.TextInput
import java.text.NumberFormat
import java.util.Locale

class CurrencyInput(context: Context, attrs: AttributeSet? = null) : TextInput(context, attrs) {
    private val textWatcher = CurrencyTextWatcher(this)

    internal val input get() = inputBinding.input
    internal val locale = Locale.getDefault()

    var rawValue: Long = 0
        internal set

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        configureCurrencyPrefix()
        configureTextInput()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        detachCurrencyTextWatcher()
    }

    private fun configureTextInput() {
        input.addTextChangedListener(textWatcher)
        input.inputType = InputType.TYPE_CLASS_NUMBER
        input.maxLines = 1
        input.filters = arrayOf(LengthFilter(16))
    }

    private fun configureCurrencyPrefix() {
        val currencySymbol = NumberFormat
            .getCurrencyInstance(locale)
            .currency
            ?.symbol

        prefixText = currencySymbol
    }

    private fun detachCurrencyTextWatcher() {
        input.removeTextChangedListener(textWatcher)
    }
}