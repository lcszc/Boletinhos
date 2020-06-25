package app.boletinhos.lifecycle

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

internal class FakeCoroutineScopeClient(
    coroutineScope: CoroutineScope
) : CoroutineScope by coroutineScope {
    fun job1() = launch { delay(100) }

    fun job2() = launch { delay(200) }

    fun job3() = launch { delay(300) }

    fun job4Async() = async { delay(999) }

    fun job5Async() = async { throw IllegalStateException("An expected error occurred. Bye.") }
}
