package app.boletinhos.bill.add

import kotlinx.coroutines.CoroutineExceptionHandler

inline fun addBillExceptionHandler(
    crossinline handler: (AddBillViewError) -> Unit
) = CoroutineExceptionHandler { _, throwable ->
    handler(AddBillViewError(throwable))
}
