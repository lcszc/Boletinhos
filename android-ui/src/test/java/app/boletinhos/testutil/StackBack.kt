package app.boletinhos.testutil

import app.boletinhos.navigation.ViewKey
import com.zhuinden.simplestack.Backstack
import com.zhuinden.simplestack.History
import com.zhuinden.simplestack.ScopedServices
import com.zhuinden.simplestack.StateChanger

object StackBack {
    private val fakeScopedServices = ScopedServices { }

    private val fakeStateChanger = StateChanger { _, completionCallback ->
        completionCallback.stateChangeComplete()
    }

    fun create(viewKey: ViewKey) = Backstack().apply {
        setScopedServices(fakeScopedServices)
        setup(History.single(viewKey))
        setStateChanger(fakeStateChanger)
    }
}
