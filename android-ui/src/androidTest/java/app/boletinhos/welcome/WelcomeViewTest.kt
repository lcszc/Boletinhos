package app.boletinhos.welcome

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import app.boletinhos.application.TestApplication
import app.boletinhos.application.injection.TestAppComponent
import app.boletinhos.main.MainActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@MediumTest
class WelcomeViewTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Inject
    lateinit var witnessRobot: WitnessRobot

    @Before
    fun setUp() {
        val testComponent = ApplicationProvider.getApplicationContext<TestApplication>()
            .appComponent() as TestAppComponent

        testComponent.inject(this)
    }

    @Test fun shouldShowWelcomeContent(): Unit = with(witnessRobot) {
        launchWelcome(withScenario = activityRule.scenario)
        checkIfTitleAndMessageIsShown()
    }

    @Test fun shouldNavigateToAddBillScreen(): Unit = with(witnessRobot) {
        launchWelcome(withScenario = activityRule.scenario)
        tapOnAddBillAction()
        checkIfNavigatedToAddBillScreen(withScenario = activityRule.scenario)
    }

    @Test fun shouldNavigateToSummaryAfterCreatingFirstBill(): Unit = with(witnessRobot) {
        launchWelcome(withScenario = activityRule.scenario)
        simulateBillCreated(withScenario = activityRule.scenario)
        checkIfNavigatedToSummary(withScenario = activityRule.scenario)
    }
}