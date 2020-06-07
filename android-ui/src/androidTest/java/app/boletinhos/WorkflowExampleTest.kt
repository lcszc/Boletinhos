package app.boletinhos

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.boletinhos.main.MainApplication
import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WorkflowExampleTest {
    @Test fun testIsRunningBoletinhoApp() {
        val context = ApplicationProvider.getApplicationContext<MainApplication>()
        assertThat(context.packageName).isEqualTo("app.boletinhos")
    }
}
