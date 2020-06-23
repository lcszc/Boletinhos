package app.boletinhos.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import app.boletinhos.bills.BillEntity
import app.boletinhos.bills.InDatabaseFetchBillService
import app.boletinhos.bills.InDatabaseSummaryService
import app.boletinhos.bills.ManageBillDao
import app.boletinhos.typeconverter.BillStatusTypeConverter
import app.boletinhos.typeconverter.LocalDateTypeConverter

@Database(entities = [BillEntity::class], version = 1)
@TypeConverters(value = [LocalDateTypeConverter::class, BillStatusTypeConverter::class])
abstract class AppDatabase : RoomDatabase() {
    internal abstract fun fetchBillService(): InDatabaseFetchBillService
    abstract fun manageBillDao(): ManageBillDao
    internal abstract fun summaryService(): InDatabaseSummaryService

    companion object {
        const val DATABASE_NAME = "boletinhos-db"
    }
}
