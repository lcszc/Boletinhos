package app.boletinhos.widget.date

import android.content.Context
import android.text.InputFilter.LengthFilter
import android.text.InputType
import android.util.AttributeSet
import app.boletinhos.time.dateOrNull
import app.boletinhos.widget.text.TextInput
import java.time.LocalDate

class DateInput(
    context: Context,
    attrs: AttributeSet? = null
) : TextInput(context, attrs) {
    private val textWatcher = DateTextWatcher(this)

    internal val input get() = inputBinding.input

    val date: LocalDate? get() = value.dateOrNull

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        configureTextInput()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        input.removeTextChangedListener(textWatcher)
    }

    private fun configureTextInput() {
        input.addTextChangedListener(textWatcher)
        input.inputType = InputType.TYPE_CLASS_DATETIME
        input.maxLines = 1
        input.filters = arrayOf(LengthFilter(MAX_INPUT_SIZE))
    }

    companion object {
        const val MAX_INPUT_SIZE = 10
    }
}
