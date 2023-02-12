package com.omdb.movie.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.omdb.movie.repository.MoviePagingSource
import com.omdb.movie.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _keywordSearchState = MutableStateFlow<String?>(null)
    private val _numberOfResultState = MutableStateFlow<Int?>(null)
    private var pageSource: MoviePagingSource? = null

    val numberOfResultState = _numberOfResultState.asStateFlow()
    val keywordSearchState = _keywordSearchState.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val moviePagingData =
        _keywordSearchState.filterNotNull().flatMapLatest { keyword ->
            return@flatMapLatest Pager(config = PagingConfig(
                initialLoadSize = 10,
                pageSize = 10,
                prefetchDistance = 1
            ), pagingSourceFactory = {
                _numberOfResultState.value = null
                MoviePagingSource(
                    movieRepository, keyword, _numberOfResultState
                ).also {
                    pageSource = it
                }
            }).flow.cachedIn(viewModelScope)
        }

    fun searchKeyword(text: String) {
        viewModelScope.launch {
            _keywordSearchState.emit(text)
        }
    }

    fun refresh() {
        viewModelScope.launch {
            pageSource?.invalidate()
            val keyword = _keywordSearchState.value
            _keywordSearchState.emit(null)
            _keywordSearchState.emit(keyword)
        }
    }

}
