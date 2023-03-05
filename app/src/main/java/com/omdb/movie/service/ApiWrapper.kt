package com.omdb.movie.service

import com.omdb.movie.data.ErrorType
import com.omdb.movie.data.Resource
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

/**
 * Handle only response for retrofit client
 */
suspend fun <T> safeCallApi(
    api: suspend () -> Response<T>
): Resource<T> {
    return try {
        val response = api.invoke()
        val body = response.body()
        if (response.isSuccessful && body != null) {
            Resource.Success(body)
        } else {
            throw HttpException(response)
        }
    } catch (throwable: Throwable) {
        val errorType = when (throwable) {
            is HttpException -> {
                val response = throwable.response()
                ErrorType.NetworkError(
                    httpCode = response?.code(),
                    message = response?.message() ?: throwable.localizedMessage
                )
            }
            is IOException -> {
                ErrorType.NetworkError(
                    message = throwable.localizedMessage
                )
            }
            else -> {
                ErrorType.ResponseError(
                    message = throwable.localizedMessage
                )
            }
        }
        Resource.Error(errorType)
    }
}
