package app.boletinhos.testutil

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import app.boletinhos.bills.ManageBillDao
import app.boletinhos.database.AppDatabase
import app.boletinhos.domain.bill.FetchBill
import app.boletinhos.domain.summary.SummaryService
import kotlinx.coroutines.asExecutor
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE, sdk = [23])
abstract class AppDatabaseTest : CoroutineTest() {
    private lateinit var appDatabase: AppDatabase

    internal lateinit var fetchBillService: FetchBill
    internal lateinit var summaryService: SummaryService
    internal lateinit var manageBillDao: ManageBillDao

    @Before fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().context

        appDatabase = Room
            .inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .setQueryExecutor(mainCoroutineRule.testDispatcher.asExecutor())
            .setTransactionExecutor(mainCoroutineRule.testDispatcher.asExecutor())
            .allowMainThreadQueries()
            .build()

        fetchBillService = appDatabase.fetchBillService()
        summaryService = appDatabase.summaryService()
        manageBillDao = appDatabase.manageBillDao()
    }

    @After fun tearDown() {
        appDatabase.clearAllTables()
        appDatabase.close()
    }
}