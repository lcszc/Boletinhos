package app.boletinhos.bill.add

import app.boletinhos.navigation.ViewKey
import kotlinx.android.parcel.Parcelize
import app.boletinhos.R.layout as Layouts

@Parcelize
class AddBillViewKey : ViewKey {
    override fun layout(): Int = Layouts.bill_add
}