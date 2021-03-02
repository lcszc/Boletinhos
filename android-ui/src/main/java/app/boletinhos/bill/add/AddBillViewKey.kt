package app.boletinhos.bill.add

import app.boletinhos.main.injection.activityRetainedComponent
import app.boletinhos.navigation.ViewKey
import com.zhuinden.simplestack.ServiceBinder
import com.zhuinden.simplestackextensions.services.DefaultServiceProvider.HasServices
import com.zhuinden.simplestackextensions.servicesktx.add
import kotlinx.android.parcel.Parcelize
import app.boletinhos.R.layout as Layouts

@Parcelize
class AddBillViewKey : ViewKey, HasServices {
    override fun layout(): Int = Layouts.bill_add

    override fun getScopeTag(): String = javaClass.name

    override fun bindServices(serviceBinder: ServiceBinder) {
        val component = serviceBinder.activityRetainedComponent
        serviceBinder.add(component.addBillViewModel())
    }
}
