package app.boletinhos.main.injection

import com.zhuinden.simplestack.GlobalServices
import common.CoroutineScopeContainer
import javax.inject.Inject
import javax.inject.Provider

class GlobalServicesFactory @Inject constructor(
    private val coroutineScopeContainer: Provider<CoroutineScopeContainer>
) : GlobalServices.Factory {
    override fun create(): GlobalServices {
        return GlobalServices.builder()
            .addService(CoroutineScopeContainer.TAG, coroutineScopeContainer.get())
            .build()
    }
}
