package app.boletinhos.summary

import app.boletinhos.domain.summary.Summary
import app.boletinhos.summary.picker.asUiOption
import java.time.Month
import app.boletinhos.R.drawable as Drawables
import app.boletinhos.R.string as Texts

internal fun createSummaries() = listOf(
    Summary(
        month = Month.JANUARY,
        year = 2019,
        totalValue = 1900,
        paids = 0,
        unpaids = 0,
        overdue = 0
    ),
    Summary(
        month = Month.FEBRUARY,
        year = 2019,
        totalValue = 1900,
        paids = 0,
        unpaids = 0,
        overdue = 0
    )
).sortedByDescending { it.id() }

internal fun summariesForPicker() = createSummaries()
    .map(Summary::asUiOption)
    .map { it.copy(isSelected = true) }

internal fun createItemsFromSummary(summary: Summary) = listOf(
    SummaryItemCardView.Model(
        iconRes = Drawables.ic_summary,
        titleRes = Texts.text_month_summary,
        titleArg = summary.displayName(),
        descriptionRes = Texts.text_bills,
        textValue = summary.formattedTotalValue(),
        kind = SummaryItemCardView.Model.Kind.MONTH_SUMMARY
    ),
    SummaryItemCardView.Model(
        iconRes = Drawables.ic_paid,
        titleRes = Texts.text_bills_paids,
        descriptionRes = Texts.text_bills,
        textValue = summary.paids.toString(),
        kind = SummaryItemCardView.Model.Kind.PAIDS
    ),

    SummaryItemCardView.Model(
        iconRes = Drawables.ic_hourglass,
        titleRes = Texts.text_bills_unpaids,
        descriptionRes = Texts.text_bills,
        textValue = summary.unpaids.toString(),
        kind = SummaryItemCardView.Model.Kind.UNPAIDS
    ),

    SummaryItemCardView.Model(
        iconRes = Drawables.ic_calendar,
        titleRes = Texts.text_bills_overdue,
        descriptionRes = Texts.text_bills,
        textValue = summary.overdue.toString(),
        kind = SummaryItemCardView.Model.Kind.OVERDUE
    )
)
