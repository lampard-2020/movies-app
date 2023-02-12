package com.omdb.movie.di

import com.omdb.movie.repository.MovieRepository
import com.omdb.movie.repository.MovieRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class MovieRepositoryModule {

    @Binds
    abstract fun bindRepository(repository: MovieRepositoryImpl): MovieRepository

}
