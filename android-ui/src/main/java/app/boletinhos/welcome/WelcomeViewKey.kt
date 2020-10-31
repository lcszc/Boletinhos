package app.boletinhos.welcome

import app.boletinhos.navigation.ViewKey
import kotlinx.android.parcel.Parcelize
import app.boletinhos.R.layout as Layouts

@Parcelize
class WelcomeViewKey : ViewKey {
    override fun layout(): Int = Layouts.welcome_view
}
