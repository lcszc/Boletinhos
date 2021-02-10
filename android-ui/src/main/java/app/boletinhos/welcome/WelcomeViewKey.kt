package app.boletinhos.welcome

import app.boletinhos.main.injection.activityRetainedComponent
import app.boletinhos.navigation.ViewKey
import com.zhuinden.simplestack.ServiceBinder
import com.zhuinden.simplestackextensions.services.DefaultServiceProvider
import com.zhuinden.simplestackextensions.servicesktx.add
import kotlinx.android.parcel.Parcelize
import app.boletinhos.R.layout as Layouts

@Parcelize
class WelcomeViewKey : ViewKey, DefaultServiceProvider.HasServices {
    override fun layout(): Int = Layouts.welcome_view

    override fun getScopeTag() = javaClass.name

    override fun bindServices(serviceBinder: ServiceBinder) {
        val component = serviceBinder.activityRetainedComponent
        serviceBinder.add(component.welcomeViewModel())
    }
}
