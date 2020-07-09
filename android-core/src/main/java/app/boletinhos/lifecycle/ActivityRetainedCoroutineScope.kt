package app.boletinhos.lifecycle

import common.ActivityRetainedScope
import common.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.SupervisorJob
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@ActivityRetainedScope
internal class ActivityRetainedCoroutineScope @Inject constructor(
    @common.ImmediateDispatcher dispatcher: CoroutineDispatcher,
    @common.MainDispatcher mainDispatcher: CoroutineDispatcher,
    @common.IoDispatcher ioDispatcher: CoroutineDispatcher
): LifecycleAwareCoroutineScope {
    override val coroutineContext: CoroutineContext = SupervisorJob() + dispatcher

    override val io: CoroutineDispatcher = ioDispatcher
    override val main: CoroutineDispatcher = mainDispatcher
}
