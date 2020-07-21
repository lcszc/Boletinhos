package app.boletinhos.application.injection

import android.content.Context
import androidx.room.Room
import app.boletinhos.database.AppDatabase
import common.AppContext
import common.AppScope
import dagger.Module
import dagger.Provides

@Module
object TestDatabaseModule {
    @Provides
    @AppScope
    internal fun provideAppDatabase(@AppContext context: Context): AppDatabase {
        return Room
            .inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .build()
    }
}