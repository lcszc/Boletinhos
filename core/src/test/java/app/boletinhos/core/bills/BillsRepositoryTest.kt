package app.boletinhos.core.bills

import app.boletinhos.core.factory.BillsFactory
import app.boletinhos.core.testutil.AppDatabaseTest
import app.boletinhos.domain.bill.BillStatus
import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import java.time.LocalDate
import java.time.Month

class BillsRepositoryTest : AppDatabaseTest() {
    private val repository by lazy {
        BillsRepository(billsDao)
    }

    @Test fun `should create new bill with unpaid status`() = runBlockingTest {
        // @given
        val expected = BillsFactory.unpaid.copy(dueDate = LocalDate.now())

        // @when
        repository.new(expected)

        // @then
        billsDao.getAll().test { bills ->
            assertThat(bills.first().status).isEqualTo(BillStatus.UNPAID)
        }
    }

    @Test fun `should create new bill with overdue status`() = runBlockingTest {
        // @given
        val expected = BillsFactory.unpaid.copy(dueDate = LocalDate.of(2020, Month.JANUARY, 3))

        // @when
        repository.new(expected)

        // @then
        billsDao.getAll().test { bills ->
            assertThat(bills.first().status).isEqualTo(BillStatus.OVERDUE)
        }
    }
}