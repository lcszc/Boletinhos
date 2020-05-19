package app.boletinhos.core.bills

import androidx.room.Entity
import androidx.room.PrimaryKey
import app.boletinhos.domain.bill.BillStatus
import java.time.LocalDate

@Entity(tableName = "bills")
data class BillEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val description: String,
    val value: Long,
    val paymentDate: LocalDate?,
    val dueDate: LocalDate,
    val status: BillStatus
)