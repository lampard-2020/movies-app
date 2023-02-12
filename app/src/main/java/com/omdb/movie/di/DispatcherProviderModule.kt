package com.omdb.movie.di

import com.omdb.movie.utils.dispatcher.DispatcherProvider
import com.omdb.movie.utils.dispatcher.DispatcherProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DispatcherProviderModule {
    @Binds
    abstract fun bindDispatcher(implementation: DispatcherProviderImpl): DispatcherProvider
}
