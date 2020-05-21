package app.boletinhos.storage.typeconverter

import androidx.room.TypeConverter
import app.boletinhos.domain.bill.BillStatus

object BillStatusTypeConverter {
    @TypeConverter
    @JvmStatic
    fun toStatus(value: String?): BillStatus? {
        if (value == null) return null
        return BillStatus.valueOf(value)
    }

    @TypeConverter
    @JvmStatic
    fun fromStatus(status: BillStatus?): String? {
        return status?.name
    }
}