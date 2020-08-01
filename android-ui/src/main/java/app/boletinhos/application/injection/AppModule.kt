package app.boletinhos.application.injection

import android.app.Application
import android.content.Context
import app.boletinhos.main.injection.ActivityRetainedComponent
import common.AppContext
import common.ImmediateDispatcher
import common.IoDispatcher
import common.MainDispatcher
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module(subcomponents = [ActivityRetainedComponent::class])
object AppModule {
    @Provides
    @AppContext
    internal fun provideContext(app: Application): Context = app

    /* rework -> multibinding */
    @Provides
    @ImmediateDispatcher
    internal fun provideImmediateDispatcher(): CoroutineDispatcher = Dispatchers.Main.immediate

    @Provides
    @MainDispatcher
    internal fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @Provides
    @IoDispatcher
    internal fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
}
