package app.boletinhos.application.injection

import android.app.Application
import android.content.Context

@dagger.Module object AppModule {
    @dagger.Provides @common.AppContext internal fun provideContext(app: Application): Context = app
}