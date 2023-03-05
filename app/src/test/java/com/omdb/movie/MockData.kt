package com.omdb.movie

import com.omdb.movie.data.domain.ListMovieResult
import com.omdb.movie.data.domain.Movie


object MockData {

    private val ListMovieData = Array(10) {
        Movie("id$it", "poster$it", "title$$it", "movie", "2022")
    }.toList()

    val ListMovieResultMock = ListMovieResult(
        search = ListMovieData,
        totalResults = ListMovieData.size
    )

    val ListMovieEmptyMock = ListMovieResult(
        search = emptyList(),
        totalResults = 0
    )

}
