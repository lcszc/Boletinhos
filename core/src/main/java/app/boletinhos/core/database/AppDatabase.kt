package app.boletinhos.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import app.boletinhos.core.bills.Bill
import app.boletinhos.core.bills.BillsDao

@Database(entities = [Bill::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun billsDao(): BillsDao
}