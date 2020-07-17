package app.boletinhos.navigation

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.get
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Lifecycle.State.DESTROYED
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import app.boletinhos.main.MainActivity
import app.boletinhos.wip.WipViewKey
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import com.zhuinden.simplestackextensions.navigatorktx.backstack
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.isActive
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import android.R.id as AndroidIds

@RunWith(AndroidJUnit4::class)
@SmallTest
class ViewCoroutineScopeTest {
    @get:Rule
    val mainActivity = ActivityScenarioRule(MainActivity::class.java)

    @Test fun shouldHaveViewCoroutineScope() {
        mainActivity.scenario.run {
            onActivity { activity ->
                val rootContainer = activity.findViewById<ViewGroup>(AndroidIds.content)

                val topView = rootContainer[0]
                assertThat(topView.viewScope.isActive).isTrue()
            }
        }
    }

    @Test fun shouldViewCoroutineScopeBeNotActiveAfterViewGetDetached() {
        mainActivity.scenario.run {
            onActivity { activity ->
                val backstack = activity.backstack
                val rootContainer = activity.findViewById<ViewGroup>(AndroidIds.content)

                val topView = rootContainer[0]
                backstack.goTo(WipViewKey("new view"))

                assertThat(topView.viewScope.isActive).isFalse()
            }
        }
    }

    @Test fun shouldViewChildrenHaveAccessToItsCoroutineScope() {
        mainActivity.scenario.run {
            onActivity { activity ->
                val rootContainer = activity.findViewById<ViewGroup>(AndroidIds.content)

                val topView = rootContainer[0] as ViewGroup
                val child1 = FakeView(activity, "my first fake view")

                topView.addView(child1)

                assertThat(child1.viewScope).isEqualTo(topView.viewScope)
            }
        }
    }

    @Test fun shouldViewCoroutineScopeGetsCancelledWhenActivityGetsDestroyed() {
        var topViewCoroutineScope: CoroutineScope? = null

        mainActivity.scenario.run {
            onActivity { activity ->
                val rootContainer = activity.findViewById<ViewGroup>(AndroidIds.content)
                val topView = rootContainer[0]
                topViewCoroutineScope = topView.viewScope
            }

            recreate()
        }

        assertThat(topViewCoroutineScope!!.isActive).isFalse()
    }

    @Test fun shouldViewChildrenBeAbleToUseItsCoroutineScope() {
        mainActivity.scenario.run {
            onActivity { activity ->
                val rootContainer = activity.findViewById<ViewGroup>(AndroidIds.content)

                val topView = rootContainer[0] as ViewGroup
                val child1 = FakeView(activity, "my awesome text")

                topView.addView(child1)

                val text = (child1[0] as TextView).text
                assertThat(text).isEqualTo("my awesome text")
            }
        }
    }
}
