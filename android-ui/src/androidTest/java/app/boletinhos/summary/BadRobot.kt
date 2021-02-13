package app.boletinhos.summary

import android.app.Application
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import app.boletinhos.R
import app.boletinhos.domain.summary.Summary
import app.boletinhos.main.MainActivity
import app.boletinhos.navigation.ViewKey
import app.boletinhos.testutil.FakeBillsFactory
import app.boletinhos.testutil.atPositionOnView
import app.boletinhos.welcome.WelcomeViewKey
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import com.zhuinden.simplestack.History
import com.zhuinden.simplestack.StateChange
import com.zhuinden.simplestackextensions.navigatorktx.backstack
import javax.inject.Inject
import app.boletinhos.R.id as Ids
import app.boletinhos.R.string as Texts

class BadRobot @Inject constructor(
    private val fakeBillsFactory: FakeBillsFactory
) {
    private lateinit var summary: Summary

    fun injectFakeSummary() = apply {
        summary = fakeBillsFactory.createFakeSummary()
    }

    fun launchView(withScenario: ActivityScenario<MainActivity>) = apply {
        withScenario.onActivity { activity ->
            activity.backstack.setHistory(History.single(SummaryViewKey()), StateChange.FORWARD)
        }
    }

    fun destroyView(withScenario: ActivityScenario<MainActivity>) = apply {
        withScenario.recreate()
    }

    fun checkIfNavigatedToWelcomeScreen(withScenario: ActivityScenario<MainActivity>) = apply {
        withScenario.onActivity { activity ->
            val backstack = activity.backstack

            assertThat(backstack.top<ViewKey>()).isInstanceOf(WelcomeViewKey::class.java)
            assertThat(backstack.getHistory<ViewKey>().size).isEqualTo(1)
        }
    }

    fun checkIfSummaryIsShown() = apply {
        val summaryTitle = ApplicationProvider.getApplicationContext<Application>()
            .resources.getString(Texts.text_month_summary, summary.displayName())

        val summaryTotalValue = summary.formattedTotalValue()

        onView(withId(R.id.summary)).run {
            check(
                matches(
                    atPositionOnView(
                        position = SUMMARY_CARD_POSITION,
                        target = Ids.text_item_title,
                        matcher = withText(summaryTitle)
                    )
                )
            )

            check(
                matches(
                    atPositionOnView(
                        position = SUMMARY_CARD_POSITION,
                        target = Ids.text_item_value,
                        matcher = withText(summaryTotalValue)
                    )
                )
            )
        }
    }

    fun checkIfPaidBillsCountIsShown() = apply {
        onView(withId(R.id.summary)).run {
            check(
                matches(
                    atPositionOnView(
                        position = PAID_BILLS_CARD_POSITION,
                        target = Ids.text_item_title,
                        matcher = withText(Texts.text_bills_paids)
                    )
                )
            )

            check(
                matches(
                    atPositionOnView(
                        position = PAID_BILLS_CARD_POSITION,
                        target = Ids.text_item_value,
                        matcher = withText(summary.paids.toString())
                    )
                )
            )
        }
    }

    fun checkIfUnpaidBillsCountIsShown() = apply {
        onView(withId(R.id.summary)).run {
            check(
                matches(
                    atPositionOnView(
                        position = UNPAID_BILLS_CARD_POSITION,
                        target = Ids.text_item_title,
                        matcher = withText(Texts.text_bills_unpaids)
                    )
                )
            )

            check(
                matches(
                    atPositionOnView(
                        position = UNPAID_BILLS_CARD_POSITION,
                        target = Ids.text_item_value,
                        matcher = withText(summary.unpaids.toString())
                    )
                )
            )
        }
    }

    fun checkIfOverdueBillsCountIsShown() = apply {
        onView(withId(R.id.summary)).run {
            check(
                matches(
                    atPositionOnView(
                        position = OVERDUE_BILLS_CARD_POSITION,
                        target = Ids.text_item_title,
                        matcher = withText(Texts.text_bills_overdue)
                    )
                )
            )

            check(
                matches(
                    atPositionOnView(
                        position = OVERDUE_BILLS_CARD_POSITION,
                        target = Ids.text_item_value,
                        matcher = withText(summary.overdue.toString())
                    )
                )
            )
        }
    }

    companion object {
        private const val SUMMARY_CARD_POSITION = 0
        private const val PAID_BILLS_CARD_POSITION = 1
        private const val UNPAID_BILLS_CARD_POSITION = 2
        private const val OVERDUE_BILLS_CARD_POSITION = 3
    }
}