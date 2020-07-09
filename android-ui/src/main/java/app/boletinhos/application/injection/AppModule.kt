package app.boletinhos.application.injection

import android.app.Application
import android.content.Context
import app.boletinhos.main.injection.ActivityRetainedComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@dagger.Module(subcomponents = [ActivityRetainedComponent::class])
object AppModule {
    @dagger.Provides
    @common.AppContext
    internal fun provideContext(app: Application): Context = app

    /* rework -> multibinding */
    @dagger.Provides
    @common.ImmediateDispatcher
    internal fun provideImmediateDispatcher(): CoroutineDispatcher = Dispatchers.Main.immediate

    @dagger.Provides
    @common.MainDispatcher
    internal fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @dagger.Provides
    @common.IoDispatcher
    internal fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
}
