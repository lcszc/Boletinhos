package app.boletinhos.storage.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import app.boletinhos.storage.bills.BillEntity
import app.boletinhos.storage.bills.BillsDao
import app.boletinhos.storage.bills.BillsSummaryDao
import app.boletinhos.storage.bills.ManageBillDao
import app.boletinhos.storage.typeconverter.BillStatusTypeConverter
import app.boletinhos.storage.typeconverter.LocalDateTypeConverter

@Database(entities = [BillEntity::class], version = 1)
@TypeConverters(value = [LocalDateTypeConverter::class, BillStatusTypeConverter::class])
abstract class AppDatabase : RoomDatabase() {
    abstract fun billsDao(): BillsDao
    abstract fun manageBillDao(): ManageBillDao
    abstract fun billsSummaryDao(): BillsSummaryDao

    companion object {
        const val DATABASE_NAME = "boletinhos-db"
    }
}
