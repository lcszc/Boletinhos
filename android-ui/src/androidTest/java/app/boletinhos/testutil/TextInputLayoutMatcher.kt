package app.boletinhos.testutil

import android.view.View
import androidx.test.espresso.matcher.BoundedMatcher
import com.google.android.material.textfield.TextInputLayout
import org.hamcrest.Description
import org.hamcrest.Matcher

fun inputLayoutHasPrefix(prefix: String): Matcher<View?> {
    return object : BoundedMatcher<View?, TextInputLayout>(TextInputLayout::class.java) {
        override fun matchesSafely(item: TextInputLayout): Boolean {
            return prefix == item.prefixText?.toString().orEmpty()
        }

        override fun describeTo(description: Description?) {
            description?.appendText("has prefix $prefix in TextInputLayout")
        }
    }
}

fun inputLayoutHasError(error: String?): Matcher<View?> {
    return object : BoundedMatcher<View?, TextInputLayout>(TextInputLayout::class.java) {
        override fun matchesSafely(item: TextInputLayout?): Boolean {
            return error == item?.error
        }

        override fun describeTo(description: Description?) {
            description?.appendText("has error $error in TextInputLayout")
        }
    }
}