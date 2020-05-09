package app.boletinhos.core.testutil

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.junit.Rule

abstract class CoroutineTest {
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    val testScope = CoroutineScope(mainCoroutineRule.testDispatcher)

    fun <T> Flow<T>.test(block: (actual: T) -> Unit) {
        mainCoroutineRule.runBlocking {
            testScope.launch {
                collect { actual ->
                    block(actual)
                }
            }.cancelAndJoin()
        }
    }
}