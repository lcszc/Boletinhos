package app.boletinhos.main

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import app.boletinhos.application.MainApplication
import app.boletinhos.application.injection.AppComponent
import app.boletinhos.main.injection.ActivityRetainedServicesFactory
import app.boletinhos.navigation.ViewStateChanger
import app.boletinhos.navigation.viewScope
import app.boletinhos.summary.SummaryViewKey
import com.zhuinden.simplestack.History
import com.zhuinden.simplestack.navigator.Navigator
import com.zhuinden.simplestackextensions.services.DefaultServiceProvider
import kotlinx.coroutines.cancel
import javax.inject.Inject
import android.R.id as AndroidIds
import app.boletinhos.R.style as Styles

class MainActivity : AppCompatActivity() {
    @Inject lateinit var activityRetainedServicesFactory: ActivityRetainedServicesFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent().inject(this)

        setTheme(Styles.App)
        super.onCreate(savedInstanceState)

        val root = findViewById<ViewGroup>(AndroidIds.content)
        val stateChanger = ViewStateChanger(this, root)

        Navigator.configure()
            .setStateChanger(stateChanger)
            .setScopedServices(DefaultServiceProvider())
            .setGlobalServices(activityRetainedServicesFactory)
            .install(this, root, History.single(SummaryViewKey()))
    }

    override fun onBackPressed() {
        if (!Navigator.onBackPressed(this)) {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        val root = findViewById<ViewGroup>(AndroidIds.content)
        root[0].viewScope.cancel()
    }

    private fun appComponent(): AppComponent {
        return (application as MainApplication).appComponent()
    }
}
