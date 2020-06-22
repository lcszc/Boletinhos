package app.boletinhos.fakes

import app.boletinhos.domain.bill.Bill
import app.boletinhos.domain.bill.BillStatus.OVERDUE
import app.boletinhos.domain.bill.BillStatus.PAID
import app.boletinhos.domain.bill.BillStatus.UNPAID
import java.time.LocalDate

object BillsFactory {
    private val now get() = LocalDate.now()

    val main = Bill(
        name = "House Electricity Bill",
        description = "Electricity",
        value = 250_00L /* 250 currency */,
        dueDate = now.plusMonths(1),
        paymentDate = null,
        status = UNPAID
    )

    val bill2 = main.copy(
        name = "Unity 3D",
        description = "Subscriptions",
        value = 99_00L
    )

    val bill3 = main.copy(
        name = "PluralSight",
        description = "Subscriptions",
        value = 59_00L
    )

    val bill4 = main.copy(
        name = "Caster.io",
        description = "Subscriptions",
        value = 29_00L
    )

    val bill5 = main.copy(
        name = "Spotify",
        description = "Subscriptions",
        value = 8_00L
    )

    val bill6 = main.copy(
        name = "Netflix",
        description = "Subscriptions",
        value = 49_90L
    )

    val bill7 = main.copy(
        name = "Cute Cats NGO",
        description = "Donations",
        value = 990_00L,
        dueDate = now.plusMonths(2)
    )

    val bill8 = main.copy(
        name = "Angry Cats NGO",
        description = "Donations",
        value = 990_00L,
        dueDate = now.plusMonths(2)
    )

    val bill9 = main.copy(
        name = "Dogs for a living NGO",
        description = "Donations",
        value = 990_00L,
        dueDate = now.plusMonths(2)
    )

    val unpaids = listOf(
        bill2,
        bill3,
        bill4,
        bill5,
        bill6,
        bill7,
        bill8,
        bill9
    )

    val paids = unpaids.map { it.copy(status = PAID) }
    val overdue = paids.map { it.copy(status = OVERDUE) }

    fun pick(quantity: Int = 1) = unpaids.shuffled().take(quantity)
}