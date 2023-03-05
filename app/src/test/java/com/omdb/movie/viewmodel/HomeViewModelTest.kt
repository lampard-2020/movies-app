package com.omdb.movie.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.omdb.movie.CoroutinesTestRule
import com.omdb.movie.MockData
import com.omdb.movie.data.ErrorType
import com.omdb.movie.data.Resource
import com.omdb.movie.main.viewmodel.HomeViewModel
import com.omdb.movie.repository.MovieRepository
import com.quadible.paging.testPages
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.stub
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
@OptIn(ExperimentalCoroutinesApi::class, ExperimentalTime::class)
class HomeViewModelTest {

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @InjectMocks
    private lateinit var viewModel: HomeViewModel

    private val repository: MovieRepository = mock()


    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `given repository return success, when VM get movie paging date, then VM emit paging data success`() =
        runTest {
            // Given
            repository.stub {
                onBlocking { searchMovie(any(), any()) } doAnswer {
                    val page = it.getArgument(1) as Int
                    Resource.Success(
                        if (page == 1) MockData.ListMovieResultMock else MockData.ListMovieEmptyMock
                    )
                }
            }

            // When
            viewModel.searchKeyword("keyword")

            // Then
            viewModel.moviePagingData.testPages {
                val pagination = awaitPages()
                assertEquals(MockData.ListMovieResultMock.search, pagination.first())

                viewModel.numberOfResultState.test {
                    assertEquals(MockData.ListMovieResultMock.totalResults, awaitItem())
                }
                ignoreRemaining()
            }

        }


    @Test
    fun `given repository return success, when VM get movie paging date, then VM emit error`() =
        runTest {
            val mockError = ErrorType.ResponseError()
            // Given
            repository.stub {
                onBlocking { searchMovie(any(), any()) } doReturn Resource.Error(mockError)
            }

            // When
            viewModel.searchKeyword("keyword")

            // Then
            viewModel.moviePagingData.testPages {
                val pagination = awaitPages()
                assertTrue(pagination.isEmpty)
                viewModel.numberOfResultState.test {
                    assertEquals(null, awaitItem())
                }
                ignoreRemaining()
            }

        }

}
