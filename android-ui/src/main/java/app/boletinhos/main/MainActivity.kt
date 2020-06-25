package app.boletinhos.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import app.boletinhos.R
import app.boletinhos.application.MainApplication
import app.boletinhos.application.injection.AppComponent
import app.boletinhos.main.injection.ActivityComponent

class MainActivity : AppCompatActivity() {
    private fun appComponent(): AppComponent {
        return (application as MainApplication).mainComponent()
    }

    private fun injection(): ActivityComponent {
        return appComponent()
            .activityComponentFactory()
            .create(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.App)
        super.onCreate(savedInstanceState)
    }
}
