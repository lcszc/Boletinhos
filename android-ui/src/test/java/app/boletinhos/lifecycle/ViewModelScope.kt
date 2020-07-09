package app.boletinhos.lifecycle

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlin.coroutines.CoroutineContext

val viewModelScope = object : LifecycleAwareCoroutineScope {
    override val coroutineContext: CoroutineContext = TestCoroutineDispatcher()
    override val io: CoroutineDispatcher = TestCoroutineDispatcher()
    override val main: CoroutineDispatcher = TestCoroutineDispatcher()
}
