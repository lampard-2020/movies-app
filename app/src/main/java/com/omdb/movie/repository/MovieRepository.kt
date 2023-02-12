package com.omdb.movie.repository

import com.omdb.movie.data.Resource
import com.omdb.movie.data.domain.ListMovieResult

interface MovieRepository {


    suspend fun searchMovie(keyword: String, page: Int): Resource<ListMovieResult>

}
