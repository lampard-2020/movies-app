package com.omdb.movie.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.omdb.movie.data.entity.SearchedKeywordEntity

@Dao
interface SearchedKeywordDao {

    @Query("SELECT * FROM searchedKeyword")
    suspend fun getAllKeywords(): List<SearchedKeywordEntity>

    @Query("DELETE FROM searchedKeyword")
    fun deleteAll()

}
