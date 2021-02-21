package app.boletinhos.widget.date

import android.view.Gravity
import android.view.KeyEvent
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import app.boletinhos.R
import app.boletinhos.testutil.TestActivity
import app.boletinhos.testutil.dateInputHasDate
import app.boletinhos.testutil.textInputHasTextValue
import org.hamcrest.Matchers
import java.time.LocalDate

class FifteenthRobot(private val scenario: ActivityScenario<TestActivity>) {
    private lateinit var dateInput: DateInput

    fun launchApp() = apply {
        launchActivityAndShowDateInput(scenario)
    }

    private fun launchActivityAndShowDateInput(
        withScenario: ActivityScenario<TestActivity>
    ) = apply {
        withScenario.onActivity { activity ->
            dateInput = activity.createDateInputView()
            activity.rootView.addView(dateInput)
        }
    }

    private fun TestActivity.createDateInputView(): DateInput {
        return DateInput(context = rootView.context).apply {
            id = VIEW_ID
            layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT).apply {
                gravity = Gravity.CENTER
            }
        }
    }

    private fun view() = Espresso.onView(Matchers.allOf(ViewMatchers.withId(VIEW_ID)))

    private fun inputView() = Espresso.onView(Matchers.allOf(ViewMatchers.withId(INPUT_VIEW_ID)))

    fun hasText(text: String) = apply {
        view().check(ViewAssertions.matches(textInputHasTextValue(text)))
    }

    fun hasDate(date: LocalDate?) = apply {
        view().check(ViewAssertions.matches(dateInputHasDate(date)))
    }

    fun type(text: String) = apply {
        inputView().perform(ViewActions.typeText(text))
    }

    fun backspace() = apply {
        inputView().perform(ViewActions.pressKey(KeyEvent.KEYCODE_DEL))
    }

    private companion object {
        private const val VIEW_ID = 15
        private const val INPUT_VIEW_ID = R.id.input
    }
}