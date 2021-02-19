package app.boletinhos.testutil

import android.view.View
import androidx.test.espresso.matcher.BoundedMatcher
import app.boletinhos.widget.text.TextInput
import org.hamcrest.Description
import org.hamcrest.Matcher

fun textInputHasTextValue(text: String) : Matcher<View?> {
    return object : BoundedMatcher<View?, TextInput>(TextInput::class.java) {
        override fun matchesSafely(item: TextInput): Boolean {
            return text == item.value.trim()
        }

        override fun describeTo(description: Description?) {
            description?.appendText("has text $text in TextInput")
        }
    }
}