package app.boletinhos.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import app.boletinhos.bill.BillEntity
import app.boletinhos.bill.InDatabaseBillService
import app.boletinhos.summary.InDatabaseSummaryService
import app.boletinhos.bill.InDatabaseManageBillService
import app.boletinhos.typeconverter.BillStatusTypeConverter
import app.boletinhos.typeconverter.LocalDateTypeConverter

@Database(entities = [BillEntity::class], version = 1)
@TypeConverters(value = [LocalDateTypeConverter::class, BillStatusTypeConverter::class])
abstract class AppDatabase : RoomDatabase() {
    internal abstract fun billService(): InDatabaseBillService
    internal abstract fun manageBillService(): InDatabaseManageBillService
    internal abstract fun summaryService(): InDatabaseSummaryService

    companion object {
        const val DATABASE_NAME = "boletinhos-db"
    }
}
