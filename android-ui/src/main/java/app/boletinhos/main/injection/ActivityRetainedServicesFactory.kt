package app.boletinhos.main.injection

import com.zhuinden.simplestack.Backstack
import com.zhuinden.simplestack.GlobalServices
import com.zhuinden.simplestackextensions.servicesktx.add
import javax.inject.Inject

class ActivityRetainedServicesFactory @Inject constructor(
    private val service: ActivityRetainedService
) : GlobalServices.Factory {
    override fun create(backstack: Backstack): GlobalServices {
        service.createComponent(backstack)

        return GlobalServices.builder()
            .add(service)
            .build()
    }
}
