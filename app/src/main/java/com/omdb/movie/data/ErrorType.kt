package com.omdb.movie.data

sealed class ErrorType {
    class ResponseError(val errorCode: String? = null, val message: String = "Response Error") :
        ErrorType()

    class NetworkError(val httpCode: Int? = null, val message: String = "Network Error") :
        ErrorType()

    class ServerError(val message: String = "Server internal error") : ErrorType()

    // For other error
    class CustomError(val throwable: Throwable?) : ErrorType()
}

fun ErrorType.message(): String {
    return when (this) {
        is ErrorType.ResponseError -> this.message
        is ErrorType.NetworkError -> this.message
        is ErrorType.ServerError -> this.message
        is ErrorType.CustomError -> this.throwable?.localizedMessage ?: "Something wrongs. Oops!"
    }
}
