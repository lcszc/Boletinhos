package app.boletinhos.main.injection

import android.app.Activity

@dagger.Subcomponent
interface ActivityComponent {
    @dagger.Subcomponent.Factory interface Factory {
        fun create(
            @dagger.BindsInstance
            @common.ActivityContext
            activity: Activity
        ): ActivityComponent
    }
}
