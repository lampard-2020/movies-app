package com.omdb.movie.data.domain

data class ListMovieResult(
    var response: Boolean = false,
    var search: List<Movie> = emptyList(),
    var totalResults: Int = 0
)
