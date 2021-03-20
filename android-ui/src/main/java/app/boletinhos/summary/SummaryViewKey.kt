package app.boletinhos.summary

import app.boletinhos.R
import app.boletinhos.main.injection.activityRetainedComponent
import app.boletinhos.navigation.ViewKey
import com.zhuinden.simplestack.ServiceBinder
import com.zhuinden.simplestackextensions.services.DefaultServiceProvider
import com.zhuinden.simplestackextensions.servicesktx.add
import kotlinx.android.parcel.Parcelize

@Parcelize
class SummaryViewKey : ViewKey, DefaultServiceProvider.HasServices {
    override fun layout() = R.layout.summary_view

    override fun getScopeTag() = javaClass.name

    override fun bindServices(serviceBinder: ServiceBinder) {
        val activityRetainedComponent = serviceBinder.activityRetainedComponent
        serviceBinder.add(activityRetainedComponent.summaryViewModel())
        serviceBinder.add(activityRetainedComponent.summaryPickerViewModel())
    }
}
