package app.boletinhos.lifecycle.injection

import app.boletinhos.lifecycle.ActivityRetainedCoroutineScope
import app.boletinhos.lifecycle.LifecycleAwareCoroutineScope
import kotlinx.coroutines.CoroutineScope

@dagger.Module
object LifecycleCoroutineScopeModule {
    @dagger.Provides
    internal fun provideCoroutineScope(impl: ActivityRetainedCoroutineScope): LifecycleAwareCoroutineScope {
        return impl
    }
}
