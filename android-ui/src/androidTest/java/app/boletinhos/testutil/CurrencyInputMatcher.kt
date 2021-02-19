package app.boletinhos.testutil

import android.view.View
import androidx.test.espresso.matcher.BoundedMatcher
import app.boletinhos.widget.currency.CurrencyInput
import org.hamcrest.Description
import org.hamcrest.Matcher

fun currencyInputHasRawValue(value: Long) : Matcher<View?> {
    return object : BoundedMatcher<View?, CurrencyInput>(CurrencyInput::class.java) {
        override fun matchesSafely(item: CurrencyInput): Boolean {
            return value == item.rawValue
        }

        override fun describeTo(description: Description?) {
            description?.appendText("has value $value in CurrencyInput")
        }
    }
}