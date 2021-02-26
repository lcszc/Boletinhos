package app.boletinhos.bill.add

import app.boletinhos.domain.bill.Bill
import app.boletinhos.domain.bill.BillGateway
import app.boletinhos.domain.bill.BillStatus
import app.boletinhos.domain.bill.BillValidator
import app.boletinhos.domain.bill.CreateBill
import app.boletinhos.lifecycle.viewModelScope
import assertk.assertThat
import assertk.assertions.containsAll
import assertk.assertions.containsExactly
import assertk.assertions.isEqualTo
import com.zhuinden.simplestack.Backstack
import com.zhuinden.simplestack.History
import com.zhuinden.simplestack.ScopedServices
import com.zhuinden.simplestackextensions.servicesktx.add
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import testutil.MainCoroutineRule
import java.time.LocalDate
import java.time.Month
import app.boletinhos.R.id as Ids
import app.boletinhos.R.string as Texts

class AddBillViewModelTest {
    @get:Rule val mainCoroutineRule = MainCoroutineRule()

    private val backstack: Backstack = Backstack()

    private val viewKey = AddBillViewKey()
    private val scopeTag = viewKey.scopeTag

    private val gateway = mockk<BillGateway>(relaxed = true)
    private val useCase = CreateBill(gateway, BillValidator())

    private lateinit var viewModel: AddBillViewModel

    private val scopedServices = ScopedServices { serviceBinder ->
        if (scopeTag == serviceBinder.scopeTag) {
            viewModel = AddBillViewModel(viewModelScope, useCase, serviceBinder.backstack)
            serviceBinder.add(viewModel)
        }
    }

    @Before fun setUp() {
        backstack.setScopedServices(scopedServices)
        backstack.setup(History.single(viewKey))
        backstack.setStateChanger { _, completionCallback ->
            completionCallback.stateChangeComplete()
        }
    }

        @Test fun `should create a bill given a valid input`() {
        val input = AddBillViewInput(
            name = "Burger King Subscription",
            description = "Daily Lunch",
            dueDate = LocalDate.of(2020, Month.DECEMBER, 8),
            value = 58_00L
        )

        val bill = Bill(
            name = input.name,
            description = input.description,
            dueDate = input.dueDate!!,
            value = input.value,
            status = BillStatus.OVERDUE,
            paymentDate = null
        )

        viewModel.onAddBillActionClick(input)

        coVerify { gateway.create(bill) }
    }

    @Test fun `should output a success message event given a valid created bill`() {
        val input = AddBillViewInput(
            name = "Burger King Subscription",
            description = "Daily Lunch",
            dueDate = LocalDate.of(2020, Month.DECEMBER, 8),
            value = 58_00L
        )

        viewModel.onAddBillActionClick(input)

        val messageEvent = runBlocking { viewModel.messages.first() }
        assertThat(messageEvent.messageRes).isEqualTo(Texts.message_bill_created)
    }

    @Test fun `should output min required name error given an invalid bill name input`() {
        val input = AddBillViewInput(
            name = "Bur",
            description = "Daily Lunch",
            dueDate = LocalDate.of(2020, Month.DECEMBER, 8),
            value = 58_00L
        )

        viewModel.onAddBillActionClick(input)

        val fieldError = runBlocking { viewModel.inputsErrors.first()!!.errors }[Ids.inputBillName]
        assertThat(fieldError?.messageRes).isEqualTo(Texts.message_bill_name_min_length_required)
    }

    @Test fun `should output max exceeded name error given an invalid bill name input`() {
        val input = AddBillViewInput(
            name = "Burger King Subscription Lorem Ipsum Dot Se Subscription At Bk Et All Subscription Subscription",
            description = "Daily Lunch",
            dueDate = LocalDate.of(2020, Month.DECEMBER, 8),
            value = 58_00L
        )

        viewModel.onAddBillActionClick(input)

        val fieldError = runBlocking { viewModel.inputsErrors.first()!!.errors }[Ids.inputBillName]
        assertThat(fieldError?.messageRes).isEqualTo(Texts.message_bill_name_max_length_exceeded)
    }

    @Test fun `should output min required description error given an invalid bill description input`() {
        val input = AddBillViewInput(
            name = "Burger King Subscription",
            description = "Daily",
            dueDate = LocalDate.of(2020, Month.DECEMBER, 8),
            value = 58_00L
        )

        viewModel.onAddBillActionClick(input)

        val fieldError = runBlocking {
            viewModel.inputsErrors.first()!!.errors
        }[Ids.inputBillDescription]

        assertThat(fieldError?.messageRes)
            .isEqualTo(Texts.message_bill_description_min_length_required)
    }

    @Test fun `should output max exceeded description error given an invalid bill description input`() {
        val input = AddBillViewInput(
            name = "Burger King Subscription",
            description = "Daily Lunch LunchLunchLunchLunchLunchLunchLunchLunchLunchLunchLunchLunchLunchLunchLunchLunchLunchLunchLunchLunchLunchLunchLunchLunchLunchLunchLunchLunchLunchLunchLunchLunchLunchLunchLunchLunchLunchLunchLunchLunchLunchLunchLunchLunch",
            dueDate = LocalDate.of(2020, Month.DECEMBER, 8),
            value = 58_00L
        )

        viewModel.onAddBillActionClick(input)

        val fieldError = runBlocking {
            viewModel.inputsErrors.first()!!.errors
        }[Ids.inputBillDescription]

        assertThat(fieldError?.messageRes)
            .isEqualTo(Texts.message_bill_description_max_length_exceeded)
    }

    @Test fun `should output min required value error given an invalid bill value input`() {
        val input = AddBillViewInput(
            name = "Burger King Subscription",
            description = "Daily Lunch",
            dueDate = LocalDate.of(2020, Month.DECEMBER, 8),
            value = 8_00L
        )

        viewModel.onAddBillActionClick(input)

        val fieldError = runBlocking { viewModel.inputsErrors.first()!!.errors }[Ids.inputBillValue]
        assertThat(fieldError?.messageRes).isEqualTo(Texts.message_bill_value_min_required)
    }

    @Test fun `should output max exceeded value error given an invalid bill value input`() {
        val input = AddBillViewInput(
            name = "Burger King Subscription",
            description = "Daily Lunch",
            dueDate = LocalDate.of(2020, Month.DECEMBER, 8),
            value = 999_998_00L
        )

        viewModel.onAddBillActionClick(input)

        val fieldError = runBlocking { viewModel.inputsErrors.first()!!.errors }[Ids.inputBillValue]
        assertThat(fieldError?.messageRes).isEqualTo(Texts.message_bill_value_max_exceeded)
    }

    @Test fun `should output invalid due date format given an invalid bill due date input`() {
        val input = AddBillViewInput(
            name = "Burger King Subscription",
            description = "Daily Lunch",
            dueDate = null,
            value = 58_00L
        )

        viewModel.onAddBillActionClick(input)

        val fieldError = runBlocking { viewModel.inputsErrors.first()!!.errors }[Ids.inputBillDueDate]
        assertThat(fieldError?.messageRes).isEqualTo(Texts.message_bill_invalid_due_date)
    }

    @Test fun `should output multiples errors given multiples errors on bill input`() {
        val input = AddBillViewInput(
            name = "B",
            description = "D",
            dueDate = LocalDate.of(2020, Month.DECEMBER, 8),
            value = 8_00L
        )

        viewModel.onAddBillActionClick(input)

        val fieldsErrors = runBlocking { viewModel.inputsErrors.first()!!.errors }
            .mapValues { it.value.messageRes }

        assertThat(fieldsErrors).containsAll(
            Ids.inputBillName to Texts.message_bill_name_min_length_required,
            Ids.inputBillDescription to Texts.message_bill_description_min_length_required,
            Ids.inputBillValue to Texts.message_bill_value_min_required
        )
    }

    @Test fun `should clear current error state when try to create a bill again`() {
        val input = AddBillViewInput(
            name = "Burger King Subscription",
            description = "D",
            dueDate = LocalDate.of(2020, Month.DECEMBER, 8),
            value = 58_00L
        )

        viewModel.onAddBillActionClick(input)

        val fieldsErrors = mutableListOf<AddBillViewError?>()
        viewModel.inputsErrors.onEach { error -> fieldsErrors += error }.launchIn(viewModelScope)

        viewModel.onAddBillActionClick(input.copy(description = "Daily Lunch", name = "BK"))

        val errors = fieldsErrors.map { viewError ->
            viewError?.errors?.mapValues { fieldError -> fieldError.value.messageRes }
        }

        assertThat(errors).containsExactly(
            mapOf(Ids.inputBillDescription to Texts.message_bill_description_min_length_required),
            null,
            mapOf(Ids.inputBillName to Texts.message_bill_name_min_length_required)
        )
    }

    @Test fun `should output previous errors from restored state`() {
        val input = AddBillViewInput(
            name = "err",
            description = "err",
            dueDate = LocalDate.of(2020, Month.DECEMBER, 9),
            value = 1_00L
        )

        viewModel.onAddBillActionClick(input)

        val viewError = runBlocking { viewModel.inputsErrors.first()!! }

        // --> State Restoration
        val newBackstack = Backstack()
        newBackstack.setScopedServices(scopedServices)
        newBackstack.setup(History.single(viewKey))
        newBackstack.fromBundle(backstack.toBundle())
        newBackstack.setStateChanger { _, completionCallback ->
            completionCallback.stateChangeComplete()
        }

        val viewModel2 = newBackstack.getService<AddBillViewModel>(
            scopeTag,
            AddBillViewModel::class.java.name
        )

        val restoredErrors = mutableListOf<AddBillViewError?>()

        viewModel2.inputsErrors
            .onEach { error -> restoredErrors += error  }
            .launchIn(viewModelScope)

        assertThat(restoredErrors.first()).isEqualTo(viewError)
    }
}