package app.boletinhos.application.injection

import android.app.Application
import android.content.Context
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@dagger.Module
object AppModule {
    @dagger.Provides
    @common.AppContext
    internal fun provideContext(app: Application): Context = app

    @dagger.Provides
    @common.UiDispatcher
    fun provideUiDispatcher(): CoroutineDispatcher = Dispatchers.Main.immediate
}
