package app.boletinhos.core.bills

import app.boletinhos.domain.Bill
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BillsRepository @Inject constructor(private val dao: BillsDao) {
    suspend fun create(bill: Bill) = Unit

    suspend fun update(bill: Bill) = Unit

    fun getAll() = Unit

    fun getByStatus() = Unit
}