package app.boletinhos.summary

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import app.boletinhos.application.TestApplication
import app.boletinhos.application.injection.TestAppComponent
import app.boletinhos.main.MainActivity
import app.boletinhos.rule.UsesDatabaseRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import testutil.MainCoroutineRule
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@MediumTest
class SummaryViewTest {
    @get:Rule val coroutinesRule = MainCoroutineRule()
    @get:Rule val activityRule = ActivityScenarioRule(MainActivity::class.java)
    @get:Rule val usesDatabaseRule = UsesDatabaseRule()

    @Inject lateinit var badRobot: BadRobot

    @Before fun setUp() {
        val testComponent = ApplicationProvider.getApplicationContext<TestApplication>()
            .appComponent() as TestAppComponent

        testComponent.inject(this)
    }

    @Test fun shouldShowLatestMonthSummaryCard() {
        badRobot.run {
            injectFakeSummary()
            launchView(withScenario = activityRule.scenario)
            checkIfSummaryIsShown()
        }
    }

    @Test fun shouldShowLatestMonthPaidBillsCount() {
        badRobot.run {
            injectFakeSummary()
            launchView(withScenario = activityRule.scenario)
            checkIfPaidBillsCountIsShown()
        }
    }

    @Test fun shouldShowLatestMonthUnpaidBillsCount() {
        badRobot.run {
            injectFakeSummary()
            launchView(withScenario = activityRule.scenario)
            checkIfUnpaidBillsCountIsShown()
        }
    }

    @Test fun shouldShowLatestMonthOverdueBillsCount() {
        badRobot.run {
            injectFakeSummary()
            launchView(withScenario = activityRule.scenario)
            checkIfOverdueBillsCountIsShown()
        }
    }

    @Test fun shouldNavigateToWelcomeScreenIfThereIsNoSummary() {
        badRobot.run {
            launchView(withScenario = activityRule.scenario)
            checkIfNavigatedToWelcomeScreen(withScenario = activityRule.scenario)
        }
    }

    @Test fun shouldKeepStateOnConfigChanges() {
        badRobot.run {
            injectFakeSummary()
            launchView(withScenario = activityRule.scenario)
            checkIfSummaryIsShown()
            destroyView(withScenario = activityRule.scenario)
            checkIfSummaryIsShown()
            checkIfPaidBillsCountIsShown()
            checkIfUnpaidBillsCountIsShown()
            checkIfOverdueBillsCountIsShown()
        }
    }

    @Test fun shouldNavigateToAddBillScreenOnClickInAddBill() {
        badRobot.run {
            launchView(withScenario = activityRule.scenario)
            tapOnAddBillAction()
            checkIfNavigatedToAddBillScreen(withScenario = activityRule.scenario)
        }
    }
}
