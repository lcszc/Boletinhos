package app.boletinhos.widget.date

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import app.boletinhos.testutil.TestActivity
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate
import java.time.Month

@RunWith(AndroidJUnit4::class)
@SmallTest
class DateInputTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(TestActivity::class.java)

    private val robot: FifteenthRobot by lazy {
        FifteenthRobot(activityRule.scenario)
    }

    @Test fun shouldFormatTypedDateInDefaultPattern(): Unit = with(robot) {
        launchApp()
        type(text = "12122021")
        hasText(text = "12/12/2021")
    }

    @Test fun shouldPutDividerInCorrectPosition(): Unit = with(robot) {
        launchApp()
        type(text = "12122")
        hasText(text = "12/12/2")
    }

    @Test fun shouldRemoveDividerIfNeeded(): Unit = with(robot) {
        launchApp()
        type(text = "12122021")
        hasText(text = "12/12/2021")
        repeat(5) { backspace() }
        hasText(text = "12/1")
    }

    @Test
    fun shouldParseDateInDefaultFormat(): Unit = with(robot) {
        launchApp()
        type(text = "12122021")
        hasDate(date = LocalDate.of(2021, Month.DECEMBER, 12))
    }

    @Test
    fun shouldFailToParseDateIfTypedInInvalidFormat(): Unit = with(robot) {
        launchApp()
        type(text = "120")
        hasDate(date = null)
        repeat(3) { backspace() }
        type(text = "12/13/2021")
        hasDate(date = null)
    }

    @Test
    @Ignore(value = "Will fail. Not implemented yet.")
    fun shouldNotBeAbleToTypeInvalidCharacters(): Unit = with(robot) {
        launchApp()
        type(text = "21//////12.,----2021")
        hasText(text = "21/12/2021")
        hasDate(date = LocalDate.of(2021, Month.DECEMBER, 21))
    }
}