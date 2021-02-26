package app.boletinhos.widget.currency

import android.content.Context
import android.text.InputFilter.LengthFilter
import android.text.InputType
import android.util.AttributeSet
import app.boletinhos.domain.currency.CurrencyMachine
import app.boletinhos.widget.text.TextInput
import java.util.Locale

class CurrencyInput @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    val locale: Locale = Locale.getDefault()
) : TextInput(context, attrs) {
    private val textWatcher = CurrencyTextWatcher(this)

    internal val input get() = inputBinding.input

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
        input.filters = arrayOf(LengthFilter(MAX_INPUT_SIZE))
    }

    private fun configureCurrencyPrefix() {
        prefixText = CurrencyMachine.currencySymbol(locale)
    }

    private fun detachCurrencyTextWatcher() {
        input.removeTextChangedListener(textWatcher)
    }

    companion object {
        const val MAX_INPUT_SIZE = 16
    }
}
