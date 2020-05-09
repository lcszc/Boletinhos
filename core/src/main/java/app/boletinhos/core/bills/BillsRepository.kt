package app.boletinhos.core.bills

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BillsRepository @Inject constructor(private val dao: BillsDao) {
    suspend fun create(bill: BillEntity) = Unit

    suspend fun update(bill: BillEntity) = Unit

    fun getAll() = Unit

    fun getByStatus() = Unit
}