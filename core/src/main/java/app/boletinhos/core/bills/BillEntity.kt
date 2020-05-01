package app.boletinhos.core.bills

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.LocalDate

@Entity(tableName = "bills")
data class BillEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int = -1,
    val name: String,
    val description: String,
    val value: Long,
    val paymentDate: LocalDate,
    val dueDate: LocalDate
)