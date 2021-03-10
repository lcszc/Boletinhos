package app.boletinhos.summary.picker

import app.boletinhos.domain.summary.Summary

internal fun Summary.asUiOption(): SummaryOption {
    return SummaryOption(
        monthName = monthDisplayName(),
        year = year,
        id = id()
    )
}
