package app.boletinhos.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import app.boletinhos.main.MainActivity
import app.boletinhos.main.injection.activityRetainedComponent
import assertk.assertAll
import assertk.assertThat
import assertk.assertions.isFalse
import assertk.assertions.isNotNull
import assertk.assertions.isSameAs
import assertk.assertions.isTrue
import com.zhuinden.simplestackextensions.navigatorktx.backstack
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.isActive
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class ActivityRetainedCoroutineScopeTest {
    @get:Rule val mainActivity = ActivityScenarioRule(MainActivity::class.java)

    @Test fun shouldLifecycleCoroutineScopeContainerSurviveRecreation() {
        var beforeRecreation: CoroutineScope? = null
        var afterRecreation: CoroutineScope? = null

        mainActivity.scenario.run {
            onActivity {
                beforeRecreation = it.backstack.activityRetainedComponent.coroutineScope()
            }

            recreate()

            onActivity {
                afterRecreation = it.backstack.activityRetainedComponent.coroutineScope()
            }
        }

        assertAll {
            assertThat(beforeRecreation).isNotNull()
            assertThat(beforeRecreation).isSameAs(afterRecreation)
            assertThat(afterRecreation!!.isActive).isTrue()
        }
    }

    // ~> I don't know if moveToState(DESTROYED) really simulates process death. Maybe!
    // But it should work just fine for our use case.
    @Test fun shouldLifecycleCoroutineScopeContainerBeCancelledAfterProcessDeath() {
        var uiCoroutineScope: CoroutineScope? = null

        mainActivity.scenario.run {
            recreate()
            moveToState(Lifecycle.State.RESUMED)
            onActivity { activity ->
                uiCoroutineScope = activity
                    .backstack
                    .activityRetainedComponent
                    .coroutineScope()
            }
            moveToState(Lifecycle.State.DESTROYED)
        }

        assertThat {
            assertThat(uiCoroutineScope).isNotNull()
            assertThat(uiCoroutineScope!!.isActive).isFalse()
        }
    }
}
