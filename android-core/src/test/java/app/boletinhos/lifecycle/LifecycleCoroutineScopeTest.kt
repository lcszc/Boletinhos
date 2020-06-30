package app.boletinhos.lifecycle

import assertk.assertThat
import assertk.assertions.isNotNull
import assertk.assertions.isTrue
import kotlinx.coroutines.cancel
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Test

class LifecycleCoroutineScopeTest {
    private val dispatcher = TestCoroutineDispatcher()
    private val lifecycleScope = LifecycleCoroutineScope(dispatcher)

    @Test fun `jobs must be cancelled when lifecycleScope gets cancelled`() {
        val client = FakeCoroutineScopeClient(lifecycleScope)

        val job1 = client.job1()
        val job2 = client.job2()
        val job3 = client.job3()

        lifecycleScope.cancel()

        assertThat(job1.isCancelled).isTrue()
        assertThat(job2.isCancelled).isTrue()
        assertThat(job3.isCancelled).isTrue()
    }

    @Test fun `jobs must be cancelled if they got launched after lifecycleScope gets cancelled`() {
        val client = FakeCoroutineScopeClient(lifecycleScope)

        lifecycleScope.cancel()

        val job1 = client.job1()
        val job2 = client.job2()
        val job3 = client.job3()

        assertThat(job1.isCancelled).isTrue()
        assertThat(job2.isCancelled).isTrue()
        assertThat(job3.isCancelled).isTrue()
    }

    @Suppress("IMPLICIT_NOTHING_AS_TYPE_PARAMETER")
    @Test fun `jobs completed exceptionally should not cancel the entire scope`() {
        val client = FakeCoroutineScopeClient(lifecycleScope)

        val success = client.job4Async()
        val failure = client.job5Async()

        runBlocking {
            try {
                failure.await()
            } catch (e: Exception) {}

            assertThat(failure.getCompletionExceptionOrNull()).isNotNull()
            assertThat(success.isActive).isTrue()
        }
    }
}
