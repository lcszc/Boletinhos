package app.boletinhos.welcome

import app.boletinhos.bill.add.AddBillViewKey
import app.boletinhos.bill.add.AddBillViewModel
import app.boletinhos.summary.SummaryViewKey
import com.zhuinden.simplestack.Backstack
import com.zhuinden.simplestack.History
import com.zhuinden.simplestack.StateChange
import javax.inject.Inject

class WelcomeViewModel @Inject constructor(
    private val backStack: Backstack
) : AddBillViewModel.OnBillCreatedListener {
    override fun onBillCreated() {
        backStack.setHistory(
            History.single(SummaryViewKey()),
            StateChange.REPLACE
        )
    }

    fun onAddBillClick() {
        backStack.goTo(AddBillViewKey())
    }
}
