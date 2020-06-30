package app.boletinhos.main

import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import app.boletinhos.R
import app.boletinhos.application.MainApplication
import app.boletinhos.application.injection.AppComponent
import app.boletinhos.main.injection.ActivityRetainedServiceFactory
import app.boletinhos.wip.WipViewKey
import com.zhuinden.simplestack.History
import com.zhuinden.simplestack.navigator.Navigator
import com.zhuinden.simplestackextensions.services.DefaultServiceProvider
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private fun appComponent(): AppComponent {
        return (application as MainApplication).appComponent()
    }

    @Inject lateinit var activityRetainedServiceFactory: ActivityRetainedServiceFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent().inject(this)

        setTheme(R.style.App)
        super.onCreate(savedInstanceState)

        val root = MainLayout(this)
        setContentView(root)

        Navigator.configure()
            .setScopedServices(DefaultServiceProvider())
            .setGlobalServices(activityRetainedServiceFactory)
            .install(this, root, History.single(WipViewKey()))
    }

    override fun onBackPressed() {
        if (!Navigator.onBackPressed(this)) {
            super.onBackPressed()
        }
    }

    private inner class MainLayout(context: Context) : FrameLayout(context) {
        init {
            layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)
        }
    }
}
