package app.boletinhos.widget.currency

import android.text.Editable
import android.text.TextWatcher
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.NumberFormat

class CurrencyTextWatcher(
    private val currencyInput: CurrencyInput,
    private val currencyPattern: Regex = "[^0-9]".toRegex()
) : TextWatcher {
    private var lastInput: String = ""

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) = Unit

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) = Unit

    override fun afterTextChanged(editable: Editable) {
        val textInput = currencyInput.input
        textInput.removeTextChangedListener(this)

        val currentText = editable.toString()

        if (currentText.isBlank()) {
            currencyInput.rawValue = 0
            textInput.setText("")
            reattachWatcher()
            return
        }

        val rawText = currentText.replace(currencyPattern, "")
        currencyInput.rawValue = rawText.toLong()

        val formattedValue = runCatching {
            val value = BigDecimal(rawText)
                .setScale(2, BigDecimal.ROUND_FLOOR)
                .divide(BigDecimal(100), BigDecimal.ROUND_FLOOR)

            value.bigDecimalValueToFormattedCurrencyValue()
        }.getOrDefault(lastInput)

        lastInput = formattedValue

        textInput.setText(formattedValue)
        textInput.setSelection(formattedValue.length)

        reattachWatcher()
    }

    // Todo: adding a text-watcher is expensive. Improve it later.
    private fun reattachWatcher() = currencyInput.input.addTextChangedListener(this)

    private fun BigDecimal.bigDecimalValueToFormattedCurrencyValue(): String {
        val formatter: DecimalFormat = NumberFormat
            .getCurrencyInstance(currencyInput.locale) as DecimalFormat

        formatter.decimalFormatSymbols = formatter
            .decimalFormatSymbols.apply { currencySymbol = "" }

        return formatter.format(this)
    }
}
