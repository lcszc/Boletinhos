package app.boletinhos.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import app.boletinhos.bills.BillEntity
import app.boletinhos.bills.BillsDao
import app.boletinhos.bills.BillsSummaryDao
import app.boletinhos.bills.ManageBillDao
import app.boletinhos.typeconverter.BillStatusTypeConverter
import app.boletinhos.typeconverter.LocalDateTypeConverter

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
