package com.omdb.movie.data.dto


import com.google.gson.annotations.SerializedName
import com.omdb.movie.data.domain.ListMovieResult

data class MovieListDto(
    @SerializedName("Response")
    var response: String?,
    @SerializedName("Search")
    var search: List<MovieDto?>?,
    @SerializedName("totalResults")
    var totalResults: String?
)


fun MovieListDto.toDomain(): ListMovieResult {
    return ListMovieResult(
        response = response == "True",
        search = search?.filterNotNull()?.map { it.toDomain() } ?: emptyList(),
        totalResults = totalResults?.toIntOrNull() ?: 0
    )
}
