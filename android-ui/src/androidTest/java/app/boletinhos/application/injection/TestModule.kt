package app.boletinhos.application.injection

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.crashlytics.FirebaseCrashlytics
import common.AppContext
import common.AppScope
import common.ImmediateDispatcher
import common.IoDispatcher
import common.MainDispatcher
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
object TestModule {
    @Provides
    @AppContext
    internal fun provideContext(app: Application): Context = app

    @Provides
    @ImmediateDispatcher
    internal fun provideImmediateDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @Provides
    @MainDispatcher
    internal fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @Provides
    @IoDispatcher
    internal fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @Provides
    @AppScope
    internal fun provideSharedPreferences(
        @AppContext context: Context
    ): SharedPreferences {
        return context.getSharedPreferences("test._.test", Context.MODE_PRIVATE)
    }

    @Provides
    @AppScope
    internal fun provideCrashlytics(): FirebaseCrashlytics {
        return FirebaseCrashlytics.getInstance()
    }
}