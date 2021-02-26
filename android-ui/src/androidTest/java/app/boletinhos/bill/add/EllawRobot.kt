package app.boletinhos.bill.add

import android.content.Context
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import app.boletinhos.R
import app.boletinhos.domain.bill.Bill
import app.boletinhos.domain.bill.BillStatus
import app.boletinhos.main.MainActivity
import app.boletinhos.testutil.FakeBillsFactory
import app.boletinhos.testutil.inputLayoutHasError
import app.boletinhos.testutil.textInputHasTextValue
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.zhuinden.simplestack.History
import com.zhuinden.simplestack.StateChange
import com.zhuinden.simplestackextensions.navigatorktx.backstack
import org.hamcrest.Matchers.allOf
import java.time.LocalDate
import java.util.Locale
import javax.inject.Inject

class EllawRobot @Inject constructor(private val billsFactory: FakeBillsFactory) {
    enum class UiField(val fieldId: Int) {
        VALUE(R.id.inputBillValue),
        NAME(R.id.inputBillName),
        DESCRIPTION(R.id.inputBillDescription),
        DUE_DATE(R.id.inputBillDueDate)
    }

    private val addBillViewKey = AddBillViewKey()

    private val context get() = ApplicationProvider.getApplicationContext<Context>()

    fun launchApp(withScenario: ActivityScenario<MainActivity>) = apply {
        withScenario.onActivity { activity ->
            Locale.setDefault(Locale.US)
            activity.backstack.setHistory(History.single(addBillViewKey), StateChange.FORWARD)
        }
    }

    fun destroyView(withScenario: ActivityScenario<MainActivity>) = apply {
        withScenario.recreate()
    }

    fun hasTextShown(textRes: Int) = apply {
        onView(allOf(withText(textRes))).check(matches(isDisplayed()))
    }

    fun hasTextIn(field: UiField, textRes: Int) = apply {
        val text = context.getString(textRes)
        onView(allOf(withId(field.fieldId))).check(matches(textInputHasTextValue(text)))
    }

    fun hasErrorIn(field: UiField, errorTextRes: Int, argValue: String? = null) = apply {
        val errorText = if (argValue != null) {
            context.getString(errorTextRes, argValue)
        }  else context.getString(errorTextRes)

        onView(allOf(withId(field.fieldId))).check(matches(inputLayoutHasError(errorText)))
    }

    fun hasNoErrorIn(field: UiField) = apply {
        onView(allOf(withId(field.fieldId))).check(matches(inputLayoutHasError(null)))
    }

    fun typeTextIn(field: UiField, text: String) = apply {
        onView(allOf(withId(R.id.input), isDescendantOfA(withId(field.fieldId)))).perform(
            typeText(text)
        )
    }

    fun tapOnCreateBill() = apply {
        closeSoftKeyboard()
        onView(withId(R.id.action_create_bill)).perform(click())
    }

    fun checkCreatedBill(
        name: String,
        description: String,
        value: Long,
        dueDate: LocalDate,
        status: BillStatus
    ) = apply {
        val createdBill = billsFactory.getRecentCreatedBill()

        val bill = Bill(
            name = name,
            description = description,
            value = value,
            dueDate = dueDate,
            paymentDate = null,
            status = status
        )

        assertThat(createdBill).isEqualTo(bill)
    }
}
