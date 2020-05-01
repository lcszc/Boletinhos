package app.boletinhos.core.testutil

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import app.boletinhos.core.bills.BillsDao
import app.boletinhos.core.database.AppDatabase
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
abstract class AppDatabaseTest {
    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    private lateinit var appDatabase: AppDatabase
    internal lateinit var billsDao: BillsDao

    @Before fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        appDatabase = Room
            .inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .setTransactionExecutor(testDispatcher.asExecutor())
            .setQueryExecutor(testDispatcher.asExecutor())
            .allowMainThreadQueries()
            .build()

        billsDao = appDatabase.billsDao()
    }

    @After fun tearDown() {
        appDatabase.clearAllTables()
        appDatabase.close()
    }
}