package app.boletinhos.welcome

import app.boletinhos.navigation.ViewKey
import app.boletinhos.wip.WipViewKey
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.zhuinden.simplestack.Backstack
import com.zhuinden.simplestack.History
import org.junit.Before
import org.junit.Test

class WelcomeViewModelTest {
    private val backstack: Backstack = Backstack()
    private val viewModel: WelcomeViewModel = WelcomeViewModel(backstack)

    @Before fun setUp() {
        backstack.setScopedServices {  }
        backstack.setup(History.single(WelcomeViewKey()))
        backstack.setStateChanger { _, completionCallback ->
            completionCallback.stateChangeComplete()
        }
    }

    @Test fun `should open add bill screen on add bill click`() {
        viewModel.onAddBillClick()
        assertThat(backstack.top<ViewKey>()).isEqualTo(WipViewKey("Add Bill"))
    }
}
