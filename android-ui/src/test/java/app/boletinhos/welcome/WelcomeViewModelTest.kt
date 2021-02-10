package app.boletinhos.welcome

import app.boletinhos.navigation.ViewKey
import app.boletinhos.testutil.StackBack
import app.boletinhos.wip.WipViewKey
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.zhuinden.simplestack.Backstack
import org.junit.Test

class WelcomeViewModelTest {
    private val backstack: Backstack = StackBack.create(WelcomeViewKey())
    private val viewModel: WelcomeViewModel = WelcomeViewModel(backstack)

    @Test fun `should open add bill screen on add bill click`() {
        viewModel.onAddBillClick()
        assertThat(backstack.top<ViewKey>()).isEqualTo(WipViewKey("Add Bill"))
    }
}
