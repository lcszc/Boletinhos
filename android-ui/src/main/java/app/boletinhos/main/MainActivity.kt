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
import com.zhuinden.simplestack.GlobalServices
import com.zhuinden.simplestack.History
import com.zhuinden.simplestack.navigator.Navigator
import com.zhuinden.simplestackextensions.services.DefaultServiceProvider
import common.CoroutineScopeContainer

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

        val root = MainLayout(this)
        setContentView(root)

        val component = injection()

        Navigator.configure()
            .setGlobalServices {
                GlobalServices.builder()
                    .addService(CoroutineScopeContainer.TAG, component.coroutineScopeContainer())
                    .build()
            }
            .setScopedServices(DefaultServiceProvider())
            .install(this, root, History.single(WipViewKey))
    }

    internal inner class MainLayout(context: Context) : FrameLayout(context) {
        init {
            layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)
        }
    }
}
