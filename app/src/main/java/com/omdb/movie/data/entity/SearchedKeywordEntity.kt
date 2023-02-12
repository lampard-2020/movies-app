package com.omdb.movie.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "searchedKeyword")
data class SearchedKeywordEntity(
    @PrimaryKey
    val id: Int = 0,
    val text: String,
    val date: String
)
