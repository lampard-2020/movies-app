package com.omdb.movie.di

import android.content.Context
import androidx.room.Room
import com.omdb.movie.database.AppDatabase
import com.omdb.movie.database.dao.SearchedKeywordDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, "local-database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideSearchDao(database: AppDatabase): SearchedKeywordDao {
        return database.searchDao()
    }

}
