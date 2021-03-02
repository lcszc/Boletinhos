package app.boletinhos.bill.add

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import app.boletinhos.databinding.BillAddContentBinding
import app.boletinhos.ext.view.getString
import app.boletinhos.ext.view.inflater
import app.boletinhos.ext.view.service
import app.boletinhos.messaging.UiEvent
import app.boletinhos.navigation.viewScope
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import app.boletinhos.R.layout as Layouts

class AddBillView(context: Context, attrs: AttributeSet? = null) : RelativeLayout(context, attrs) {
    private val binding by lazy {
        BillAddContentBinding.bind(this)
    }

    private val inputs get() = sequenceOf(
        binding.inputBillName,
        binding.inputBillDescription,
        binding.inputBillValue,
        binding.inputBillDueDate
    )

    private val viewModel by service<AddBillViewModel>()

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        showContentPreview()
        configureActions()

        viewModel.inputsErrors.handleInputsErrors()
        viewModel.messages.handleUiEvents()
    }

    private fun showContentPreview() {
        if (!isInEditMode) return
        inflater.inflate(Layouts.bill_add_content, this, true)
    }

    private fun configureActions() = with(binding) {
        actionCreateBill.setOnClickListener {
            val input = AddBillViewInput(
                value = inputBillValue.rawValue,
                name = inputBillName.value,
                description = inputBillDescription.value,
                dueDate = inputBillDueDate.date
            )

            viewModel.onAddBillActionClick(input)
        }

        toolbar.setNavigationOnClickListener { viewModel.onBackClick() }
    }

    private fun Flow<AddBillViewError?>.handleInputsErrors() {
        onEach { viewError -> showErrors(viewError?.errors) }.launchIn(viewScope)
    }

    private fun showErrors(errors: Map<Int, AddBillInputFieldError>?) {
        if (errors == null || errors.isEmpty()) {
            inputs.forEach { it.error = null }
            return
        }

        errors.forEach { (inputId, fieldError) ->
            if (inputId == View.NO_ID) {
                showSnackbar(fieldError.messageRes)
                return@forEach
            }

            val (messageRes, value) = fieldError
            val message = value?.let { getString(messageRes, it) } ?: getString(messageRes)

            inputs.first { it.id == inputId }.error = message
        }
    }

    private fun Flow<UiEvent.ResourceMessage>.handleUiEvents() {
        onEach { (messageRes) -> showSnackbar(messageRes) }.launchIn(viewScope)
    }

    private fun showSnackbar(messageRes: Int) {
        Snackbar.make(parent as View, messageRes, Snackbar.LENGTH_SHORT).show()
    }
}
