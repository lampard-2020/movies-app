package com.omdb.movie.service

import com.omdb.movie.data.dto.MovieListDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET("/")
    suspend fun searchMovie(
        @Query("apikey") apikey: String = "b9bd48a6",
        @Query("s") keyword: String = "",
        @Query("type") type: String = "movie",
        @Query("page") page: Int = 1
    ): Response<MovieListDto>

}
