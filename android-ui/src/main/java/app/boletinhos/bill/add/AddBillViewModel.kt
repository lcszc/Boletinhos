package app.boletinhos.bill.add

import app.boletinhos.domain.bill.Bill
import app.boletinhos.domain.bill.BillStatus
import app.boletinhos.domain.bill.CreateBill
import app.boletinhos.domain.bill.error.BillInvalidDueDateException
import app.boletinhos.lifecycle.LifecycleAwareCoroutineScope
import app.boletinhos.messaging.UiEvent.ResourceMessage
import com.zhuinden.simplestack.Backstack
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import app.boletinhos.R.string as Texts

class AddBillViewModel @Inject constructor(
    private val coroutineScope: LifecycleAwareCoroutineScope,
    private val createBill: CreateBill,
    private val backstack: Backstack
) : CoroutineScope by coroutineScope {
    private val inputsErrorsStates = MutableSharedFlow<AddBillViewError?>(replay = 1)
    val inputsErrors: Flow<AddBillViewError?> = inputsErrorsStates

    private val messagesEvents = Channel<ResourceMessage>()
    val messages: Flow<ResourceMessage> = messagesEvents.receiveAsFlow()

    private val exceptionHandler = addBillExceptionHandler { viewError ->
        launch(coroutineScope.main) {
            inputsErrorsStates.emit(viewError)
        }
    }

    fun onAddBillActionClick(input: AddBillViewInput) = launch(exceptionHandler) {
        tryToCreateBill(input = input)
        sendSuccessMessageAndGoBack()
    }

    fun onBackClick() {
        backstack.goBack()
    }

    private suspend fun tryToCreateBill(input: AddBillViewInput) {
        inputsErrorsStates.emit(null)
        val bill = input.toBill()
        createBill(bill = bill)
    }

    private suspend fun sendSuccessMessageAndGoBack() {
        messagesEvents.send(ResourceMessage(Texts.message_bill_created))
        backstack.goBack()
    }

    private fun AddBillViewInput.toBill(): Bill {
        requireNotNull(dueDate) { throw BillInvalidDueDateException }

        return  Bill(
            name = name,
            description = description,
            value = value,
            dueDate = dueDate,
            paymentDate = null,
            status = BillStatus.UNPAID
        )
    }
}
