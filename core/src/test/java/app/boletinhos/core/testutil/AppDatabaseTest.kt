package app.boletinhos.core.testutil

import android.os.Build
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import app.boletinhos.core.bills.BillsDao
import app.boletinhos.core.database.AppDatabase
import kotlinx.coroutines.asExecutor
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
abstract class AppDatabaseTest {
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var appDatabase: AppDatabase
    internal lateinit var billsDao: BillsDao

    @Before fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().context

        appDatabase = Room
            .inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .setQueryExecutor(mainCoroutineRule.testDispatcher.asExecutor())
            .setTransactionExecutor(mainCoroutineRule.testDispatcher.asExecutor())
            .allowMainThreadQueries()
            .build()

        billsDao = appDatabase.billsDao()
    }

    @After fun tearDown() {
        appDatabase.clearAllTables()
        appDatabase.close()
    }
}