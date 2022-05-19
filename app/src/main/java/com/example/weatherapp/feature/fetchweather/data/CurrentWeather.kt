package com.example.weatherapp.feature.fetchweather.data

import com.squareup.moshi.Json

data class CurrentWeather(
    val temp: Double,
    val weather: List<Weather>,
    val sunrise: Long,
    val sunset: Long,
    val pressure: Int,
    val humidity: Int,
    val visibility: Int,
    @Json(name = "wind_speed") val windSpeed: Double
)