package app.boletinhos.bill.add

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.rules.ActivityScenarioRule
import app.boletinhos.R
import app.boletinhos.application.TestApplication
import app.boletinhos.application.injection.TestAppComponent
import app.boletinhos.bill.add.EllawRobot.UiField
import app.boletinhos.domain.bill.Bill
import app.boletinhos.domain.bill.BillStatus
import app.boletinhos.domain.currency.CurrencyMachine
import app.boletinhos.main.MainActivity
import app.boletinhos.rule.UsesDatabaseRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import testutil.MainCoroutineRule
import java.time.LocalDate
import java.time.Month
import javax.inject.Inject

class AddBillViewTest {
    @get:Rule val coroutinesRule = MainCoroutineRule()
    @get:Rule val activityRule = ActivityScenarioRule(MainActivity::class.java)
    @get:Rule val usesDatabaseRule = UsesDatabaseRule()

    @Inject lateinit var ellaw: EllawRobot

    @Before fun setUp() {
        val testComponent = ApplicationProvider.getApplicationContext<TestApplication>()
            .appComponent() as TestAppComponent

        testComponent.inject(this)
    }

    @Test fun shouldCreateBill() {
        with(ellaw) {
            launchApp(activityRule.scenario)
            typeTextIn(field = UiField.VALUE, text = "5800")
            typeTextIn(field = UiField.NAME, text = "Spotify Subscription")
            typeTextIn(field = UiField.DESCRIPTION, text = "Streaming Services")
            typeTextIn(field = UiField.DUE_DATE, text = "12122020")
            tapOnCreateBill()
            hasTextShown(R.string.message_bill_created)
            checkCreatedBill(
                name = "Spotify Subscription",
                description = "Streaming Services",
                value = 58_00L,
                dueDate = LocalDate.of(2020, Month.DECEMBER, 12),
                status = BillStatus.OVERDUE
            )
        }
    }

    @Test fun shouldShowInvalidDueDateError() {
        with(ellaw) {
            launchApp(activityRule.scenario)
            tapOnCreateBill()
            hasErrorIn(field = UiField.DUE_DATE, errorTextRes = R.string.message_bill_invalid_due_date)
            hasNoErrorIn(field = UiField.NAME)
            hasNoErrorIn(field = UiField.DESCRIPTION)
            hasNoErrorIn(field = UiField.VALUE)
        }
    }

    @Test fun shouldShowMinValueRequiredError() {
        with(ellaw) {
            launchApp(activityRule.scenario)
            typeTextIn(field = UiField.NAME, text = "Spotify Subscription")
            typeTextIn(field = UiField.DESCRIPTION, text = "Streaming Services")
            typeTextIn(field = UiField.DUE_DATE, text = "12122020")
            tapOnCreateBill()
            hasErrorIn(
                field = UiField.VALUE,
                errorTextRes = R.string.message_bill_value_min_required,
                argValue = CurrencyMachine.formatFromRawValue(Bill.MINIMUM_VALUE)
            )
            hasNoErrorIn(field = UiField.NAME)
            hasNoErrorIn(field = UiField.DESCRIPTION)
            hasNoErrorIn(field = UiField.DUE_DATE)
        }
    }

    @Test fun shouldShowMaxValueExceededError() {
        with(ellaw) {
            launchApp(activityRule.scenario)
            typeTextIn(field = UiField.VALUE, text = "99999999")
            typeTextIn(field = UiField.NAME, text = "Spotify Subscription")
            typeTextIn(field = UiField.DESCRIPTION, text = "Streaming Services")
            typeTextIn(field = UiField.DUE_DATE, text = "12122020")
            tapOnCreateBill()
            hasErrorIn(
                field = UiField.VALUE,
                errorTextRes = R.string.message_bill_value_max_exceeded,
                argValue = CurrencyMachine.formatFromRawValue(Bill.MAXIMUM_VALUE)
            )
            hasNoErrorIn(field = UiField.NAME)
            hasNoErrorIn(field = UiField.DESCRIPTION)
            hasNoErrorIn(field = UiField.DUE_DATE)
        }
    }

    @Test fun shouldShowNameMinCountRequiredError() {
        with(ellaw) {
            launchApp(activityRule.scenario)
            typeTextIn(field = UiField.VALUE, text = "5800")
            typeTextIn(field = UiField.NAME, text = "Spot")
            typeTextIn(field = UiField.DESCRIPTION, text = "Streaming Services")
            typeTextIn(field = UiField.DUE_DATE, text = "12122020")
            tapOnCreateBill()
            hasErrorIn(
                field = UiField.NAME,
                errorTextRes = R.string.message_bill_name_min_length_required,
                argValue = Bill.MINIMUM_NAME_COUNT.toString()
            )
            hasNoErrorIn(field = UiField.VALUE)
            hasNoErrorIn(field = UiField.DESCRIPTION)
            hasNoErrorIn(field = UiField.DUE_DATE)
        }
    }

    @Test fun shouldShowNameMaxCountExceededError() {
        with(ellaw) {
            launchApp(activityRule.scenario)
            typeTextIn(field = UiField.VALUE, text = "5800")
            typeTextIn(field = UiField.NAME, text = "Spotiiiiifyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy")
            typeTextIn(field = UiField.DESCRIPTION, text = "Streaming Services")
            typeTextIn(field = UiField.DUE_DATE, text = "12122020")
            tapOnCreateBill()
            hasErrorIn(
                field = UiField.NAME,
                errorTextRes = R.string.message_bill_name_max_length_exceeded,
                argValue = Bill.MAXIMUM_NAME_COUNT.toString()
            )
            hasNoErrorIn(field = UiField.VALUE)
            hasNoErrorIn(field = UiField.DESCRIPTION)
            hasNoErrorIn(field = UiField.DUE_DATE)
        }
    }

    @Test fun shouldShowDescriptionMinCountRequiredError() {
        with(ellaw) {
            launchApp(activityRule.scenario)
            typeTextIn(field = UiField.VALUE, text = "5800")
            typeTextIn(field = UiField.NAME, text = "Spotify Subscription")
            typeTextIn(field = UiField.DESCRIPTION, text = "Stre")
            typeTextIn(field = UiField.DUE_DATE, text = "12122020")
            tapOnCreateBill()
            hasErrorIn(
                field = UiField.DESCRIPTION,
                errorTextRes = R.string.message_bill_description_min_length_required,
                argValue = Bill.MINIMUM_DESCRIPTION_COUNT.toString()
            )
            hasNoErrorIn(field = UiField.VALUE)
            hasNoErrorIn(field = UiField.NAME)
            hasNoErrorIn(field = UiField.DUE_DATE)
        }
    }

    @Test fun shouldShowDescriptionMaxCountExceededError() {
        with(ellaw) {
            launchApp(activityRule.scenario)
            typeTextIn(field = UiField.VALUE, text = "5800")
            typeTextIn(field = UiField.NAME, text = "Spotify Subscription")
            typeTextIn(field = UiField.DESCRIPTION, text = "Streaming Serviceeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeees")
            typeTextIn(field = UiField.DUE_DATE, text = "12122020")
            tapOnCreateBill()
            hasErrorIn(
                field = UiField.DESCRIPTION,
                errorTextRes = R.string.message_bill_description_max_length_exceeded,
                argValue = Bill.MAXIMUM_DESCRIPTION_COUNT.toString()
            )
            hasNoErrorIn(field = UiField.VALUE)
            hasNoErrorIn(field = UiField.NAME)
            hasNoErrorIn(field = UiField.DUE_DATE)
        }
    }

    @Test fun shouldShowMultipleErrorsOnUi() {
        with(ellaw) {
            launchApp(activityRule.scenario)
            typeTextIn(field = UiField.VALUE, text = "100")
            typeTextIn(field = UiField.NAME, text = "SpOo")
            typeTextIn(field = UiField.DESCRIPTION, text = "Streaming Serviceeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeees")
            typeTextIn(field = UiField.DUE_DATE, text = "12122020")
            tapOnCreateBill()
            hasErrorIn(
                field = UiField.NAME,
                errorTextRes = R.string.message_bill_name_min_length_required,
                argValue = Bill.MINIMUM_NAME_COUNT.toString()
            )
            hasErrorIn(
                field = UiField.VALUE,
                errorTextRes = R.string.message_bill_value_min_required,
                argValue = CurrencyMachine.formatFromRawValue(Bill.MINIMUM_VALUE)
            )
            hasErrorIn(
                field = UiField.DESCRIPTION,
                errorTextRes = R.string.message_bill_description_max_length_exceeded,
                argValue = Bill.MAXIMUM_DESCRIPTION_COUNT.toString()
            )
            hasNoErrorIn(field = UiField.DUE_DATE)
        }
    }

    @Test fun shouldRestoreErrorsFromPreviousState() {
        with(ellaw) {
            launchApp(activityRule.scenario)

            typeTextIn(field = UiField.VALUE, text = "100")
            typeTextIn(field = UiField.NAME, text = "SpO")
            typeTextIn(field = UiField.DESCRIPTION, text = "Streaming Serviceeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeees")
            typeTextIn(field = UiField.DUE_DATE, text = "12122020")

            tapOnCreateBill()

            hasErrorIn(
                field = UiField.NAME,
                errorTextRes = R.string.message_bill_name_min_length_required,
                argValue = Bill.MINIMUM_NAME_COUNT.toString()
            )

            hasErrorIn(
                field = UiField.VALUE,
                errorTextRes = R.string.message_bill_value_min_required,
                argValue = CurrencyMachine.formatFromRawValue(Bill.MINIMUM_VALUE)
            )

            hasErrorIn(
                field = UiField.DESCRIPTION,
                errorTextRes = R.string.message_bill_description_max_length_exceeded,
                argValue = Bill.MAXIMUM_DESCRIPTION_COUNT.toString()
            )

            hasNoErrorIn(field = UiField.DUE_DATE)

            destroyView(activityRule.scenario)

            // --> restored states
            hasErrorIn(
                field = UiField.NAME,
                errorTextRes = R.string.message_bill_name_min_length_required,
                argValue = Bill.MINIMUM_NAME_COUNT.toString()
            )

            hasErrorIn(
                field = UiField.VALUE,
                errorTextRes = R.string.message_bill_value_min_required,
                argValue = CurrencyMachine.formatFromRawValue(Bill.MINIMUM_VALUE)
            )

            hasErrorIn(
                field = UiField.DESCRIPTION,
                errorTextRes = R.string.message_bill_description_max_length_exceeded,
                argValue = Bill.MAXIMUM_DESCRIPTION_COUNT.toString()
            )

            hasNoErrorIn(field = UiField.DUE_DATE)
        }
    }
}
