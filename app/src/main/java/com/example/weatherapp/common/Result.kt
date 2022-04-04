package com.example.weatherapp.common

sealed class Result<out T : Any?> {
    data class Success<out T : Any?>(val data: T) : Result<T>()
    data class Error(val message: Exception?) : Result<Nothing>()
}

inline fun <T : Any?> performRequest(block: () -> T): Result<T> {
    return try {
        Result.Success(block())
    } catch (ex: Exception) {
        Result.Error(ex)
    }
}