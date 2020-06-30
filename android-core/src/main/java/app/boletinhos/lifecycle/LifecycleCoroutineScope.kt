package app.boletinhos.lifecycle

import common.ActivityRetainedScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@ActivityRetainedScope
internal class LifecycleCoroutineScope @Inject constructor(
    @common.UiDispatcher dispatcher: CoroutineDispatcher
): CoroutineScope {
    override val coroutineContext: CoroutineContext = SupervisorJob() + dispatcher
}
