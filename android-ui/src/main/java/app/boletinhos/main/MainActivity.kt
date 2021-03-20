package app.boletinhos.main

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import app.boletinhos.application.MainApplication
import app.boletinhos.application.injection.AppComponent
import app.boletinhos.main.injection.ActivityRetainedServicesFactory
import app.boletinhos.navigation.ModalBottomSheetViewStateChanger
import app.boletinhos.summary.SummaryViewKey
import com.zhuinden.simplestack.History
import com.zhuinden.simplestack.navigator.Navigator
import com.zhuinden.simplestackextensions.services.DefaultServiceProvider
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
        val stateChanger = ModalBottomSheetViewStateChanger(this, root, windowManager)
        lifecycle.addObserver(stateChanger)

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

    private fun appComponent(): AppComponent {
        return (application as MainApplication).appComponent()
    }
}
