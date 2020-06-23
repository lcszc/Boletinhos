package app.boletinhos.bill

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update
import app.boletinhos.domain.bill.Bill
import app.boletinhos.domain.bill.ManageBillService

@Dao internal interface InDatabaseManageBillService : ManageBillService {
    @Insert(entity = BillEntity::class)
    override suspend fun create(bill: Bill)

    @Update(entity = BillEntity::class)
    override suspend fun pay(bill: Bill)
}