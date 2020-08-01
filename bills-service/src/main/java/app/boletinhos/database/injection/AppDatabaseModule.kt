package app.boletinhos.database.injection

import android.content.Context
import androidx.room.Room
import app.boletinhos.database.AppDatabase
import common.AppContext
import common.AppScope
import dagger.Module
import dagger.Provides

@Module
object AppDatabaseModule {
    @Provides
    @AppScope
    internal fun provideAppDatabase(
        @AppContext context: Context
    ): AppDatabase {
        return Room
            .databaseBuilder(context, AppDatabase::class.java, AppDatabase.DATABASE_NAME)
            .build()
    }
}
