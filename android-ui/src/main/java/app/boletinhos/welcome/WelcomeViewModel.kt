package app.boletinhos.welcome

import app.boletinhos.wip.WipViewKey
import com.zhuinden.simplestack.Backstack
import javax.inject.Inject

class WelcomeViewModel @Inject constructor(
    private val backStack: Backstack
) {
    fun onAddBillClick() {
        backStack.goTo(WipViewKey(title = "Add Bill"))
    }
}
