package app.boletinhos.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import app.boletinhos.core.bills.BillEntity
import app.boletinhos.core.bills.BillsDao
import app.boletinhos.core.typeconverter.BillStatusTypeConverter
import app.boletinhos.core.typeconverter.LocalDateTypeConverter

@Database(entities = [BillEntity::class], version = 1)
@TypeConverters(value = [LocalDateTypeConverter::class, BillStatusTypeConverter::class])
abstract class AppDatabase : RoomDatabase() {
    abstract fun billsDao(): BillsDao
}