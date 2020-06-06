package app.boletinhos

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WorkflowExampleTest {
    @Test fun shouldContextMatch() {
        val context = InstrumentationRegistry.getInstrumentation().context
        assertThat("app.boletinhos").isEqualTo(context.packageName)
    }
}
