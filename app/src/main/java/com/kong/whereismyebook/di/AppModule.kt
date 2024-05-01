package com.kong.whereismyebook.di

import android.content.Context
import androidx.room.Room
import com.kong.whereismyebook.data.local.LibraryDAO
import com.kong.whereismyebook.data.local.LibraryDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): LibraryDatabase {
        return Room.databaseBuilder(context, LibraryDatabase::class.java, "library.db")
            .build()
    }
    @Singleton
    @Provides
    fun provideLibraryDao(database: LibraryDatabase): LibraryDAO {
        return database.libraryDao()
    }
}