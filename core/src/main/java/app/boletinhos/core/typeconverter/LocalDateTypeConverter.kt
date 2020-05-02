package app.boletinhos.core.typeconverter

import androidx.room.TypeConverter
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

object LocalDateTypeConverter {
    private val formatter = DateTimeFormatter.ofPattern("d/M/yyyy")

    @TypeConverter
    @JvmStatic
    fun toLocalDateTime(value: String?): LocalDate? {
        if (value == null) return null
        return formatter.parse(value, LocalDate::from)
    }

    @TypeConverter
    @JvmStatic
    fun fromLocalDateTime(date: LocalDate?) = date?.format(formatter)
}