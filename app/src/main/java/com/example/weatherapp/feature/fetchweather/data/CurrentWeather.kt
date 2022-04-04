package com.example.weatherapp.feature.fetchweather.data

import com.squareup.moshi.Json
import java.util.*

data class CurrentWeather(
    @Json(name = "dt") val currentTime: Date,
    val temp: Double,
    val weather: List<Weather>,
    val sunrise: Date,
    val sunset: Date,
    val pressure: Int,
    val humidity: Int,
    val visibility: Int,
    @Json(name = "wind_speed") val windSpeed: Double
)