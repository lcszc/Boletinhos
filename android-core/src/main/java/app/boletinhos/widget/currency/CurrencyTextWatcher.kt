package app.boletinhos.widget.currency

import android.text.Editable
import android.text.TextWatcher
import app.boletinhos.domain.currency.CurrencyMachine

@Suppress("ForbiddenComment")
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
            CurrencyMachine.formatFromRawValueAndRemoveSymbol(
                rawText.toLong(),
                currencyInput.locale
            )
        }.getOrDefault(lastInput)

        lastInput = formattedValue

        textInput.setText(formattedValue)
        textInput.setSelection(formattedValue.length)

        reattachWatcher()
    }

    // Todo: adding a text-watcher is expensive. Improve it later.
    private fun reattachWatcher() = currencyInput.input.addTextChangedListener(this)
}
