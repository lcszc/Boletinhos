package app.boletinhos.widget

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import app.boletinhos.testutil.TestActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Locale

@RunWith(AndroidJUnit4::class)
@SmallTest
class CurrencyInputTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(TestActivity::class.java)

    private val brLocale = Locale("pt", "Br")
    private val robot: QuindimRobot by lazy {
        QuindimRobot(activityRule.scenario)
    }

    @Test fun shouldShowUSDCurrencyInPrefix(): Unit = with(robot) {
        launchApp()
        type("1")
        checkIfCurrencySymbolIsShown("$")
    }

    @Test fun shouldShowBrazilianCurrencyInPrefix(): Unit = with(robot) {
        launchApp(withLocale = brLocale)
        type("1")
        checkIfCurrencySymbolIsShown("R$")
    }

    @Test fun shouldHaveCorrectRawValue(): Unit = with(robot) {
        launchApp()
        type("120")
        hasText("1.20")
        hasRawValue(120)
    }

    @Test fun shouldHaveCorrectRawValueInDifferentLocale(): Unit = with(robot) {
        launchApp(withLocale = brLocale)
        type("120")
        hasText("1,20")
        hasRawValue(120)
    }

    @Test fun shouldFormatTextInCorrectUSDFormat(): Unit = with(robot) {
        launchApp()
        type("999999")
        hasText("9,999.99")
    }

    @Test fun shouldFormatTextInCorrectBRLFormat(): Unit = with(robot) {
        launchApp(withLocale = brLocale)
        type("999999")
        hasText("9.999,99") // (output is always R$ 0,00 instead of $0.00)
    }

    @Test fun shouldDoNothingWhenTypingInvalidValues(): Unit = with(robot) {
        launchApp()
        type("1")
        hasText("0.01")
        type("-MidNiUqClub")
        type("-")
        hasText("0.01")
    }

    @Test fun shouldDeleteValue(): Unit = with(robot) {
        launchApp()
        type("111")
        hasText("1.11")
        backspace()
        hasText("0.11")
        backspace()
        hasText("0.01")
        backspace()
        hasText("0.00")
    }
}