package app.boletinhos.time

import app.boletinhos.widget.date.DateInput
import java.time.DateTimeException
import java.time.LocalDate
import java.time.format.DateTimeFormatter

val String.dateOrNull: LocalDate?
    get() = parseTextAsLocalDateInternal()

private val defaultFormatter = DateTimeFormatter.ofPattern("d/MM/yyyy")

private fun String.parseTextAsLocalDateInternal(): LocalDate? {
    if (isEmpty() || length < DateInput.MAX_INPUT_SIZE) return null

    return try {
        LocalDate.parse(this, defaultFormatter)
    } catch (e: DateTimeException) {
        null
    }
}
