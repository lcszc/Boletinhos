package app.boletinhos.di

import android.content.Context
import androidx.room.Room
import app.boletinhos.database.AppDatabase
import javax.inject.Singleton

@dagger.Module object AppDatabaseModule {
    @dagger.Provides @Singleton internal fun provideAppDatabase(context: Context): AppDatabase {
        return Room
            .databaseBuilder(context, AppDatabase::class.java, AppDatabase.DATABASE_NAME)
            .build()
    }
}