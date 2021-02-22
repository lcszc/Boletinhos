package app.boletinhos.welcome

import app.boletinhos.bill.add.AddBillViewKey
import com.zhuinden.simplestack.Backstack
import javax.inject.Inject

class WelcomeViewModel @Inject constructor(
    private val backStack: Backstack
) {
    fun onAddBillClick() {
        backStack.goTo(AddBillViewKey())
    }
}
