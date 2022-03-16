package com.example.weatherapp.network

sealed class Result<out T : Any> {
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val message: Exception?) : Result<Nothing>()
}