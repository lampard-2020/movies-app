package com.omdb.movie.di

import com.google.gson.GsonBuilder
import com.omdb.movie.BuildConfig
import com.omdb.movie.constant.ApiService
import com.omdb.movie.service.MovieService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    fun provideMovieService(retrofit: Retrofit): MovieService {
        return retrofit.create(
            MovieService::class.java
        )
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient, gsonConverter: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiService.API_BASE_PATH)
            .addConverterFactory(gsonConverter)
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideOkhttp(): OkHttpClient {
        val httpBuilder = OkHttpClient.Builder()
        httpBuilder
            .connectTimeout(ApiService.TIMEOUT_TIME, TimeUnit.SECONDS)
            .readTimeout(ApiService.TIMEOUT_TIME, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            httpBuilder.addInterceptor(interceptor)
            // Add Auth2 interceptor if API need authorised
        }
        return httpBuilder.build()
    }

    @Provides
    @Singleton
    fun provideGsonFactory(): GsonConverterFactory {
        return GsonConverterFactory
            .create(
                GsonBuilder()
                    .setLenient()
                    .disableHtmlEscaping()
                    .create()
            )
    }

    //    ?apikey=b9bd48a6&s=Marvel&type=movie

}
