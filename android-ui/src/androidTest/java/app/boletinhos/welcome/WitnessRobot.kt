package app.boletinhos.welcome

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import app.boletinhos.R
import app.boletinhos.bill.add.AddBillViewKey
import app.boletinhos.main.MainActivity
import app.boletinhos.navigation.ViewKey
import app.boletinhos.summary.SummaryViewKey
import assertk.assertThat
import assertk.assertions.isInstanceOf
import com.zhuinden.simplestack.History
import com.zhuinden.simplestack.StateChange
import com.zhuinden.simplestackextensions.navigatorktx.backstack
import com.zhuinden.simplestackextensions.servicesktx.lookup
import javax.inject.Inject

class WitnessRobot @Inject constructor() {
    private val welcomeViewKey = WelcomeViewKey()

    fun launchWelcome(withScenario: ActivityScenario<MainActivity>) = apply {
        withScenario.onActivity { activity ->
            activity.backstack.setHistory(History.single(welcomeViewKey), StateChange.FORWARD)
        }
    }

    fun checkIfTitleAndMessageIsShown() = apply {
        val title = R.string.text_welcome_title
        val message = R.string.text_welcome_message
        val isViewDisplayed = matches(isDisplayed())

        onView(ViewMatchers.withText(title)).check(isViewDisplayed)
        onView(ViewMatchers.withText(message)).check(isViewDisplayed)
    }

    fun checkIfNavigatedToAddBillScreen(withScenario: ActivityScenario<MainActivity>) = apply {
        withScenario.onActivity { activity ->
            val backstack = activity.backstack
            assertThat(backstack.top<ViewKey>()).isInstanceOf(AddBillViewKey::class.java)
        }
    }

    fun checkIfNavigatedToSummary(withScenario: ActivityScenario<MainActivity>) = apply {
        withScenario.onActivity { activity ->
            val backstack = activity.backstack
            assertThat(backstack.top<ViewKey>()).isInstanceOf(SummaryViewKey::class.java)
        }
    }

    fun tapOnAddBillAction() = apply {
        onView(ViewMatchers.withId(R.id.action_add_bill)).perform(click())
    }

    fun simulateBillCreated(withScenario: ActivityScenario<MainActivity>) = apply {
        withScenario.onActivity { activity ->
            val welcomeViewModel = activity.backstack.lookup<WelcomeViewModel>()
            welcomeViewModel.onBillCreated()
        }
    }
}
