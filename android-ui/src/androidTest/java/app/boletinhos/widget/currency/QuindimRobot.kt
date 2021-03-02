package app.boletinhos.widget.currency

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
import app.boletinhos.testutil.currencyInputHasRawValue
import app.boletinhos.testutil.inputLayoutHasPrefix
import app.boletinhos.testutil.textInputHasTextValue
import org.hamcrest.Matchers
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
        Locale.setDefault(locale)
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
            layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT).apply {
                gravity = Gravity.CENTER
            }
        }
    }

    private fun view() = Espresso.onView(Matchers.allOf(ViewMatchers.withId(VIEW_ID)))

    private fun inputView() = Espresso.onView(Matchers.allOf(ViewMatchers.withId(INPUT_VIEW_ID)))

    fun checkIfCurrencySymbolIsShown(expectedSymbol: String) = apply {
        view().check(ViewAssertions.matches(inputLayoutHasPrefix(expectedSymbol)))
    }

    fun hasText(text: String) = apply {
        view().check(ViewAssertions.matches(textInputHasTextValue(text)))
    }

    fun hasRawValue(value: Long) = apply {
        view().check(ViewAssertions.matches(currencyInputHasRawValue(value)))
    }

    fun type(text: String) = apply {
        inputView().perform(ViewActions.typeText(text))
    }

    fun backspace() = apply {
        inputView().perform(ViewActions.pressKey(KeyEvent.KEYCODE_DEL))
    }

    private companion object {
        private const val VIEW_ID = 8
        private const val INPUT_VIEW_ID = R.id.input
    }
}