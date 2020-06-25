package app.boletinhos.lifecycle

import com.zhuinden.simplestack.ScopedServices
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext

internal class LifecycleCoroutineScope(
    dispatcher: CoroutineDispatcher
): CoroutineScope, ScopedServices.Registered {
    override val coroutineContext: CoroutineContext = SupervisorJob() + dispatcher

    override fun onServiceRegistered() = Unit

    override fun onServiceUnregistered() = cancel()
}
