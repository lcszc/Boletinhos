package app.boletinhos.lifecycle

import com.zhuinden.simplestack.ScopedServices
import common.CoroutineScopeContainer
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

internal class LifecycleCoroutineScope @Inject constructor(
    @common.UiDispatcher dispatcher: CoroutineDispatcher
): CoroutineScopeContainer, ScopedServices.Registered {
    override val coroutineContext: CoroutineContext = SupervisorJob() + dispatcher

    override fun onServiceRegistered() = Unit

    override fun onServiceUnregistered() = cancel()
}
