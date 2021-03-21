package app.boletinhos.summary.picker

import app.boletinhos.main.injection.activityRetainedComponent
import app.boletinhos.navigation.ViewKey
import com.zhuinden.simplestack.ServiceBinder
import com.zhuinden.simplestackextensions.services.DefaultServiceProvider.HasServices
import com.zhuinden.simplestackextensions.servicesktx.add
import kotlinx.android.parcel.Parcelize
import app.boletinhos.R.layout as Layouts

@Parcelize data class SummaryPickerViewKey(
    val placeHolder: String = ""
) : ViewKey.ModalBottomSheet, HasServices {
    override fun layout(): Int = Layouts.summary_picker

    override fun getScopeTag(): String = javaClass.name

    override fun bindServices(serviceBinder: ServiceBinder) {
        val component = serviceBinder.activityRetainedComponent
        serviceBinder.add(component.summaryPickerViewModel())
    }
}
