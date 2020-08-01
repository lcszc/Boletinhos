package app.boletinhos.testutil

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import app.boletinhos.database.AppDatabase
import app.boletinhos.domain.bill.BillGateway
import app.boletinhos.domain.bill.BillService
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

    internal lateinit var billService: BillService
    internal lateinit var summaryService: SummaryService
    internal lateinit var billGateway: BillGateway

    @Before fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().context

        appDatabase = Room
            .inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .setQueryExecutor(mainCoroutineRule.testDispatcher.asExecutor())
            .setTransactionExecutor(mainCoroutineRule.testDispatcher.asExecutor())
            .allowMainThreadQueries()
            .build()

        billService = appDatabase.billService()
        summaryService = appDatabase.summaryService()
        billGateway = appDatabase.billGateway()
    }

    @After fun tearDown() {
        appDatabase.clearAllTables()
        appDatabase.close()
    }
}