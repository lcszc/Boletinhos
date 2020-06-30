package app.boletinhos.lifecycle.injection

import app.boletinhos.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.CoroutineScope

@dagger.Module
object LifecycleCoroutineScopeModule {
    @dagger.Provides
    @common.UiCoroutineScope
    internal fun provideCoroutineScope(impl: LifecycleCoroutineScope): CoroutineScope = impl
}
