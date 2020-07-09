package app.boletinhos.lifecycle

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope

interface LifecycleAwareCoroutineScope : CoroutineScope {
    val io: CoroutineDispatcher
    val main: CoroutineDispatcher
}
