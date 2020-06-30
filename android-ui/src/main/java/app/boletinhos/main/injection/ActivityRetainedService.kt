package app.boletinhos.main.injection

import android.app.Application
import app.boletinhos.application.MainApplication
import com.zhuinden.simplestack.ScopedServices
import kotlinx.coroutines.cancel
import javax.inject.Inject

class ActivityRetainedService @Inject constructor(
    private val application: Application
) : ScopedServices.Registered {
    lateinit var component: ActivityRetainedComponent
        private set


    override fun onServiceRegistered() {
        component = (application as MainApplication).appComponent()
            .activityRetainedComponentFactory().create()
    }

    override fun onServiceUnregistered() {
        component.coroutineScope().cancel()
    }
}
