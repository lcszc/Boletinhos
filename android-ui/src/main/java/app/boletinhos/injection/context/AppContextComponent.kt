package app.boletinhos.injection.context

import android.app.Application
import dagger.BindsInstance
import dagger.Component

@AppContextScope
@Component
interface AppContextComponent {
    fun application(): Application

    @Component.Factory interface Factory {
        fun create(@BindsInstance app: Application): AppContextComponent
    }
}