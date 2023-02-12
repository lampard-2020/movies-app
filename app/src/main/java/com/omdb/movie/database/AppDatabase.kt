package com.omdb.movie.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.omdb.movie.data.entity.SearchedKeywordEntity
import com.omdb.movie.database.dao.SearchedKeywordDao

@Database(entities = [SearchedKeywordEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun searchDao(): SearchedKeywordDao

}
