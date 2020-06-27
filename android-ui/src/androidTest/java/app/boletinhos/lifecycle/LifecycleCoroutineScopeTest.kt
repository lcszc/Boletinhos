package app.boletinhos.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import app.boletinhos.main.MainActivity
import assertk.assertAll
import assertk.assertThat
import assertk.assertions.isFalse
import assertk.assertions.isNotNull
import assertk.assertions.isSameAs
import assertk.assertions.isTrue
import com.zhuinden.simplestackextensions.navigatorktx.backstack
import common.CoroutineScopeContainer
import kotlinx.coroutines.isActive
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class LifecycleCoroutineScopeTest {
    @get:Rule val mainActivity = ActivityScenarioRule(MainActivity::class.java)

    @Test fun shouldLifecycleCoroutineScopeContainerSurviveRecreation() {
        var beforeRecreation: CoroutineScopeContainer? = null
        var afterRecreation: CoroutineScopeContainer? = null

        mainActivity.scenario.run {
            onActivity {
                beforeRecreation = it.backstack.lookupService(CoroutineScopeContainer.TAG)
            }

            recreate()

            onActivity {
                afterRecreation = it.backstack.lookupService(CoroutineScopeContainer.TAG)
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
        var coroutineScopeContainer: CoroutineScopeContainer? = null

        mainActivity.scenario.run {
            recreate()
            moveToState(Lifecycle.State.RESUMED)
            onActivity { activity ->
                coroutineScopeContainer = activity
                    .backstack
                    .lookupService<CoroutineScopeContainer>(CoroutineScopeContainer.TAG)
            }
            moveToState(Lifecycle.State.DESTROYED)
        }

        assertThat {
            assertThat(coroutineScopeContainer).isNotNull()
            assertThat(coroutineScopeContainer!!.isActive).isFalse()
        }
    }
}