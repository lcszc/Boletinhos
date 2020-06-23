package app.boletinhos.domain.summary

import java.time.Month

data class Summary(
    val month: Month,
    val year: Int,
    val totalValue: Long,
    val paids: Int,
    val unpaids: Int,
    val overdue: Int
)
