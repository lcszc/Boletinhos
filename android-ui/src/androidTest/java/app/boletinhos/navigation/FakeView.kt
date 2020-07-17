package app.boletinhos.navigation

import android.content.Context
import android.widget.FrameLayout
import android.widget.TextView
import kotlinx.coroutines.launch

class FakeView(context: Context, private val textContent: String) : FrameLayout(context) {
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        val textView = TextView(context)
        addView(textView)

        viewScope.launch {
            textView.text = textContent
        }
    }
}
