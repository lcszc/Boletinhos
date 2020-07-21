package app.boletinhos.runner

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import app.boletinhos.application.TestApplication

class TestRunner : AndroidJUnitRunner() {
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, TestApplication::class.java.name, context)
    }
}
