package app.boletinhos.testutil

import android.view.View
import androidx.test.espresso.matcher.BoundedMatcher
import app.boletinhos.widget.date.DateInput
import org.hamcrest.Description
import org.hamcrest.Matcher
import java.time.LocalDate

fun dateInputHasDate(date: LocalDate?) : Matcher<View?> {
    return object : BoundedMatcher<View?, DateInput>(DateInput::class.java) {
        override fun matchesSafely(item: DateInput): Boolean {
            return date == item.date
        }

        override fun describeTo(description: Description?) {
            description?.appendText("date is $date in DateInput")
        }
    }
}