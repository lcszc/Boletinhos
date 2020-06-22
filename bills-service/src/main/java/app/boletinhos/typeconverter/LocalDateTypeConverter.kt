package app.boletinhos.typeconverter

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.Month
import java.time.format.DateTimeFormatter

object LocalDateTypeConverter {
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    @TypeConverter
    @JvmStatic
    fun toLocalDateTime(value: String?): LocalDate? {
        if (value == null) return null
        return formatter.parse(value, LocalDate::from)
    }

    @TypeConverter
    @JvmStatic
    fun fromLocalDateTime(date: LocalDate?) = date?.format(formatter)

    @TypeConverter
    @JvmStatic
    fun toMonth(value: String?): Month? {
        val month = value?.toIntOrNull() ?: return null
        return Month.of(month)
    }
}
