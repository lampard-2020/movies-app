package com.omdb.movie.repository

import com.omdb.movie.data.Resource
import com.omdb.movie.data.domain.ListMovieResult
import com.omdb.movie.data.dto.toDomain
import com.omdb.movie.service.MovieService
import com.omdb.movie.service.safeCallApi
import com.omdb.movie.utils.dispatcher.DispatcherProvider
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val service: MovieService,
    private val dispatcher: DispatcherProvider,
) : MovieRepository {


    override suspend fun searchMovie(keyword: String, page: Int): Resource<ListMovieResult> {
        val result = safeCallApi {
            service.searchMovie(keyword = keyword, page = page)
        }
        return when (result) {
            is Resource.Success -> {
                Resource.Success(result.value.toDomain())
            }
            is Resource.Error -> {
                Resource.Error(result.errorType)
            }
        }
    }

}
