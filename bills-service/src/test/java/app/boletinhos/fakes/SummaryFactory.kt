package app.boletinhos.fakes

import app.boletinhos.domain.bill.BillStatus
import app.boletinhos.domain.summary.Summary
import java.time.LocalDate
import java.time.Month

object SummaryFactory {
    private val defaultBills = BillsFactory.unpaids + BillsFactory.paids + BillsFactory.overdue

    private val augustBills =
        defaultBills.map { it.copy(dueDate = LocalDate.of(2020, Month.AUGUST, 8)) }
    private val novemberBills =
        defaultBills.map { it.copy(dueDate = LocalDate.of(2020, Month.NOVEMBER, 8)) }
    private val decemberBills =
        defaultBills.map { it.copy(dueDate = LocalDate.of(2020, Month.DECEMBER, 8)) }

    val bills = augustBills + novemberBills + decemberBills

    val august = Summary(
        month = Month.AUGUST,
        year = 2020,
        totalValue = augustBills.map { it.value }.sum(),
        paids = augustBills.filter { it.status == BillStatus.PAID }.count(),
        unpaids = augustBills.filter { it.status == BillStatus.UNPAID }.count(),
        overdue = augustBills.filter { it.status == BillStatus.OVERDUE }.count()
    )

    val november = Summary(
        month = Month.NOVEMBER,
        year = 2020,
        totalValue = novemberBills.map { it.value }.sum(),
        paids = novemberBills.filter { it.status == BillStatus.PAID }.count(),
        unpaids = novemberBills.filter { it.status == BillStatus.UNPAID }.count(),
        overdue = novemberBills.filter { it.status == BillStatus.OVERDUE }.count()
    )

    val december = Summary(
        month = Month.DECEMBER,
        year = 2020,
        totalValue = decemberBills.map { it.value }.sum(),
        paids = decemberBills.filter { it.status == BillStatus.PAID }.count(),
        unpaids = decemberBills.filter { it.status == BillStatus.UNPAID }.count(),
        overdue = decemberBills.filter { it.status == BillStatus.OVERDUE }.count()
    )
}