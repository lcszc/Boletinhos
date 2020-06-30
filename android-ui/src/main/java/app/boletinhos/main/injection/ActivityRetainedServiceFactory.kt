package app.boletinhos.main.injection

import com.zhuinden.simplestack.GlobalServices
import com.zhuinden.simplestackextensions.servicesktx.add
import javax.inject.Inject

class ActivityRetainedServiceFactory @Inject constructor(
    private val service: ActivityRetainedService
) : GlobalServices.Factory {
    override fun create(): GlobalServices {
        return GlobalServices.builder()
            .add(service)
            .build()
    }
}
