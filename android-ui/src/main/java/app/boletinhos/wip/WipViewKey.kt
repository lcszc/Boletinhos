package app.boletinhos.wip

import app.boletinhos.navigation.ViewKey
import kotlinx.android.parcel.Parcelize
import app.boletinhos.R.layout as Layouts

@Parcelize
data class WipViewKey(val title: String = "") : ViewKey {
    override fun layout() = Layouts.wip_view
}
