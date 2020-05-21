package app.boletinhos.core.testutil

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.junit.Rule

abstract class CoroutineTest {
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    fun <T> Flow<T>.test(block: (actual: T) -> Unit) {
        mainCoroutineRule.runBlocking { scope ->
            scope.launch {
                collect { actual ->
                    block(actual)
                }
            }.cancelAndJoin()
        }
    }
}