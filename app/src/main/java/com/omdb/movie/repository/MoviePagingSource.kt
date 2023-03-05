package com.omdb.movie.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.omdb.movie.data.Resource
import com.omdb.movie.data.domain.Movie
import com.omdb.movie.data.message
import kotlinx.coroutines.flow.MutableStateFlow

class MoviePagingSource(
    private val movieRepository: MovieRepository,
    private val keyword: String,
    private val numberOfResult: MutableStateFlow<Int?>,
) : PagingSource<Int, Movie>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            // Start refresh at page 1 if undefined.
            val pageNumber = params.key ?: 1
            when (val result = movieRepository.searchMovie(keyword, pageNumber)) {
                is Resource.Success -> {
                    val nextPage = pageNumber + 1
                    val nextKey = if (result.value.search.isEmpty()) null else nextPage
                    if (pageNumber == 1) {
                        numberOfResult.emit(result.value.totalResults)
                    }
                    LoadResult.Page(
                        data = result.value.search, prevKey = null, // Only paging forward.
                        nextKey = nextKey
                    )
                }
                is Resource.Error -> {
                    if (pageNumber == 1) {
                        numberOfResult.emit(null)
                    }
                    LoadResult.Error(
                        Throwable(result.errorType.message())
                    )
                }
            }

        } catch (e: Exception) {
            numberOfResult.emit(null)
            LoadResult.Error(
                Throwable(e.message)
            )
        }
    }


    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? = null

}
