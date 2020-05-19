package app.boletinhos.domain.bill

import java.time.LocalDate

data class BillSummary(
    val date: LocalDate,
    val totalValue: Long,
    val paids: Int,
    val unpaids: Int,
    val overdue: Int
)