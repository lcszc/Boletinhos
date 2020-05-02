package app.boletinhos.core.bills

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import app.boletinhos.core.typeconverter.BillStatusTypeConverter
import app.boletinhos.core.typeconverter.LocalDateTypeConverter
import org.threeten.bp.LocalDate

@Entity(tableName = "bills")
data class Bill(
    val name: String,
    val description: String,
    val value: Long,
    val paymentDate: LocalDate?,
    val dueDate: LocalDate,
    val status: BillStatus
) {
    @PrimaryKey(autoGenerate = true) var uid: Int = 0
}