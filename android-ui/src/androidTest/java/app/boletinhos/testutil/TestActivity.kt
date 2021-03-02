package app.boletinhos.testutil

import android.app.Activity
import android.os.Bundle
import android.widget.FrameLayout
import android.R.id as AndroidIds

class TestActivity : Activity() {
    lateinit var rootView: FrameLayout
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rootView = findViewById(AndroidIds.content)
    }
}