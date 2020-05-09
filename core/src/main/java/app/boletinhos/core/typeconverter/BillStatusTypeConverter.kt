package app.boletinhos.core.typeconverter

import androidx.room.TypeConverter
import app.boletinhos.domain.BillStatus

object BillStatusTypeConverter {
    @TypeConverter
    @JvmStatic
    fun toStatus(value: Int?): BillStatus? {
        if (value == null) return null
        return BillStatus.values().find { status -> status.code.value == value }
    }

    @TypeConverter
    @JvmStatic
    fun fromStatus(status: BillStatus?): Int? {
        return status?.code?.value
    }
}