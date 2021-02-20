package app.boletinhos.widget.date

import android.text.Editable
import android.text.TextWatcher
import java.io.File

class DateTextWatcher(
    private val dateInput: DateInput
) : TextWatcher {
    override fun afterTextChanged(s: Editable?) = Unit

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        val textInput = dateInput.input
        textInput.removeTextChangedListener(this)

        val currentText = textInput.text?.toString().orEmpty()
        val newText = currentText
            .take(10)
            .putOrRemoveDividerIfNeeded(start, before)

        textInput.setText(newText)
        textInput.setSelection(newText.length)
        textInput.addTextChangedListener(this)
    }

    private val divider = File.separator
    private val dayDividerPosition = 2
    private val monthDividerPosition = 5

    private fun String.putOrRemoveDividerIfNeeded(start: Int, before: Int): String {
        if (length == dayDividerPosition || length == monthDividerPosition) {
            val position = length
            val isTyping = before <= position && start < position
            return if (isTyping) this + divider else dropLast(1)
        }

        return this
    }
}