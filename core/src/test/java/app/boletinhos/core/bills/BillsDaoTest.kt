package app.boletinhos.core.bills

import app.boletinhos.core.factory.BillsFactory
import app.boletinhos.core.testutil.AppDatabaseTest
import app.boletinhos.core.testutil.MainCoroutineRule
import app.boletinhos.core.testutil.runBlocking
import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test

class BillsDaoTest : AppDatabaseTest() {
    @Test fun `should insert and get inserted bill`() = runBlockingTest {
        // @given a unpaid bill
        val expected = BillsFactory.unpaid

        // @when inserting it to the app database
        billsDao.insert(expected)

        // @then it should be added in the database
        val actual = async {
            billsDao.getAll()
                .take(1)
                .toList()
                .first()
        }

        assertThat(actual.await()).isEqualTo(expected)
    }
}