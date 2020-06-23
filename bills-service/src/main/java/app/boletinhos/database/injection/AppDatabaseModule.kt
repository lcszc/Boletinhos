package app.boletinhos.database.injection

import android.content.Context
import androidx.room.Room
import app.boletinhos.database.AppDatabase
import javax.inject.Singleton

@dagger.Module object AppDatabaseModule {
    @dagger.Provides @Singleton internal fun provideAppDatabase(
        @common.AppContext context: Context
    ): AppDatabase {
        return Room
            .databaseBuilder(context, AppDatabase::class.java, AppDatabase.DATABASE_NAME)
            .build()
    }
}