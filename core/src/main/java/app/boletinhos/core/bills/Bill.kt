package app.boletinhos.core.bills

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import app.boletinhos.core.typeconverter.LocalDateTypeConverter
import org.threeten.bp.LocalDate

@Entity(tableName = "bills")
@TypeConverters(value = [LocalDateTypeConverter::class])
data class Bill(
    @PrimaryKey(autoGenerate = true) val uid: Int = -1,
    val name: String,
    val description: String,
    val value: Long,
    val paymentDate: LocalDate?,
    val dueDate: LocalDate
)