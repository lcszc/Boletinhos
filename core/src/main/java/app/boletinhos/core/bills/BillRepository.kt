package app.boletinhos.core.bills

import javax.inject.Inject

class BillRepository @Inject constructor(private val dao: BillsDao) {
    suspend fun create(bill: Bill) = Unit

    suspend fun update(bill: Bill) = Unit

    fun getAll() = Unit

    fun getByStatus() = Unit
}