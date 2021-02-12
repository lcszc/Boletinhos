package app.boletinhos.testutil

import app.boletinhos.navigation.ViewKey
import com.zhuinden.simplestack.Backstack
import com.zhuinden.simplestack.History
import com.zhuinden.simplestack.ScopedServices
import com.zhuinden.simplestack.StateChanger
import com.zhuinden.statebundle.StateBundle

object StackBack {
    val scopedServices = ScopedServices { }

    val stateChanger = StateChanger { _, completionCallback ->
        completionCallback.stateChangeComplete()
    }

    fun create(
        viewKey: ViewKey,
        scopedServices: ScopedServices = this.scopedServices,
        bundle: StateBundle? = null
    ) = Backstack().apply {
        setScopedServices(scopedServices)
        setup(History.single(viewKey))
        setStateChanger(stateChanger)
        fromBundle(bundle)
    }
}
