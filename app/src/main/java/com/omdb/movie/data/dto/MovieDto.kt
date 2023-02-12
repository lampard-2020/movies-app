package com.omdb.movie.data.dto

import com.google.gson.annotations.SerializedName
import com.omdb.movie.data.domain.Movie

data class MovieDto(
    @SerializedName("imdbID")
    var imdbID: String? = null,
    @SerializedName("Poster")
    var poster: String? = null,
    @SerializedName("Title")
    var title: String? = null,
    @SerializedName("Type")
    var type: String? = null,
    @SerializedName("Year")
    var year: String? = null
)

fun MovieDto.toDomain(): Movie {
    return Movie(
        imdbID = imdbID.orEmpty(),
        poster = poster.orEmpty(),
        title = title.orEmpty(),
        type = type.orEmpty(),
        year = year.orEmpty(),
    )
}
