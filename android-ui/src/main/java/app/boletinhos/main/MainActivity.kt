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
import app.boletinhos.main.injection.ActivityComponent
import app.boletinhos.wip.WipViewKey
import com.zhuinden.simplestack.History
import com.zhuinden.simplestack.navigator.Navigator
import com.zhuinden.simplestackextensions.services.DefaultServiceProvider

class MainActivity : AppCompatActivity() {
    private fun appComponent(): AppComponent {
        return (application as MainApplication).mainComponent()
    }

    private fun injector(): ActivityComponent {
        return appComponent().activityComponentFactory()
            .create(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val activityComponent = injector()

        setTheme(R.style.App)
        super.onCreate(savedInstanceState)

        val root = MainLayout(this)
        setContentView(root)

        Navigator.configure()
            .setGlobalServices(activityComponent.globalServicesFactory())
            .setScopedServices(DefaultServiceProvider())
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
