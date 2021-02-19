package app.boletinhos.widget

import android.view.Gravity
import android.view.KeyEvent
import android.widget.FrameLayout.LayoutParams
import android.widget.FrameLayout.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout.LayoutParams.WRAP_CONTENT
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.pressKey
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import app.boletinhos.R
import app.boletinhos.testutil.TestActivity
import app.boletinhos.testutil.currencyInputHasRawValue
import app.boletinhos.testutil.inputLayoutHasPrefix
import app.boletinhos.testutil.textInputHasTextValue
import app.boletinhos.widget.currency.CurrencyInput
import org.hamcrest.Matchers.allOf
import java.util.Locale

/* Android Q @_@ */
class QuindimRobot(private val scenario: ActivityScenario<TestActivity>) {
    private lateinit var locale: Locale
    private lateinit var currencyInput: CurrencyInput

    fun launchApp(withLocale: Locale = Locale.US) = apply {
        configureLocale(withLocale)
        launchActivityAndShowCurrencyInput(scenario)
    }

    private fun configureLocale(locale: Locale) {
        this.locale = locale
    }

    private fun launchActivityAndShowCurrencyInput(
        withScenario: ActivityScenario<TestActivity>
    ) = apply {
        withScenario.onActivity { activity ->
            currencyInput = activity.createCurrencyInputView()
            activity.rootView.addView(currencyInput)
        }
    }

    private fun TestActivity.createCurrencyInputView(): CurrencyInput {
        return CurrencyInput(context = rootView.context, locale = locale).apply {
            id = VIEW_ID
            layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT).apply {
                gravity = Gravity.CENTER
            }
        }
    }

    private fun view() = onView(allOf(withId(VIEW_ID)))

    private fun inputView() = onView(allOf(withId(INPUT_VIEW_ID)))

    fun checkIfCurrencySymbolIsShown(expectedSymbol: String) = apply {
        view().check(matches(inputLayoutHasPrefix(expectedSymbol)))
    }

    fun hasText(text: String) = apply {
        view().check(matches(textInputHasTextValue(text)))
    }

    fun hasRawValue(value: Long) = apply {
        view().check(matches(currencyInputHasRawValue(value)))
    }

    fun type(text: String) = apply {
        inputView().perform(typeText(text))
    }

    fun backspace() = apply {
        inputView().perform(pressKey(KeyEvent.KEYCODE_DEL))
    }

    private companion object {
        private const val VIEW_ID = 8
        private const val INPUT_VIEW_ID = R.id.input
    }
}