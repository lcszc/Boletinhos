package app.boletinhos.domain.bill

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFailure
import assertk.assertions.isInstanceOf
import assertk.assertions.isSuccess
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class CreateBillTest {
    private lateinit var createBill: CreateBill

    @Before fun setUp() {
        createBill = CreateBill(FakeDBBillGateway)
    }

    @Test fun `should throw exception when bill's value is lower than minimum`() {
        runBlocking {
            // given
            val bill = BillFactory.get().copy(value = 5_00L)

            // when
            val call = suspend {
                createBill(bill)
            }

            // then
            assertThat {
                call()
            }.isFailure().isInstanceOf(InvalidMinimumBillValueException::class.java)
        }
    }

    @Test fun `should throw exception when bill's value is higher than maximum`() {
        runBlocking {
            // given
            val bill = BillFactory.get().copy(value = 350_000_00L)

            // when
            val call = suspend {
                createBill(bill)
            }

            // then
            assertThat {
                call()
            }.isFailure().isInstanceOf(InvalidMaximumBillValueException::class.java)
        }
    }

    @Test fun `should throw exception when bill's name is too short`() {
        runBlocking {
            // given
            val bill = BillFactory.get().copy(name = "a")

            // when
            val call = suspend {
                createBill(bill)
            }

            // then
            assertThat {
                call()
            }.isFailure().isInstanceOf(BillNameTooShortException::class.java)
        }
    }

    @Test fun `should throw exception when bill's name is too long`() {
        runBlocking {
            // given
            val bill = BillFactory.get().copy(name = "A_VERY_VERY_LONG_BILL_NAME_THAT_SHOULD_NOT_BE_ACCEPTED")

            // when
            val call = suspend {
                createBill(bill)
            }

            // then
            assertThat {
                call()
            }.isFailure().isInstanceOf(BillNameTooLongException::class.java)
        }
    }

    @Test fun `should throw exception when bill's description is too short`() {
        runBlocking {
            // given
            val bill = BillFactory.get().copy(description = "house")

            // when
            val call = suspend {
                createBill(bill)
            }

            // then
            assertThat {
                call()
            }.isFailure().isInstanceOf(BillDescriptionTooShortException::class.java)
        }
    }

    @Test fun `should throw exception when bill's description is too long`() {
        runBlocking {
            // given
            val bill = BillFactory.get().copy(description = "A_VERY_LONG_BILL_DESCRIPTION_THAT_SHOULD_NOT_BE_ACCEPTED_BECAUSE_IT_DOES_NOT_MATCH_OUR_STANDARDS_BECAUSE_WE_DO_NOT_KNOW_YET_A_COOL_LIMIT_FOR_DESCRIPTIONS")

            // when
            val call = suspend {
                createBill(bill)
            }

            // then
            assertThat {
                call()
            }.isFailure().isInstanceOf(BillDescriptionTooLongException::class.java)
        }
    }

    @Test fun `should create bill with success`() {
        runBlocking {
            // given
            val bill = BillFactory.get()

            // when
            val call = suspend {
                createBill(bill)
            }

            // then
            assertThat {
                call()
            }.isSuccess()
        }
    }
}