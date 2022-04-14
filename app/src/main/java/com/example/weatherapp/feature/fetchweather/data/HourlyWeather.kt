package com.example.weatherapp.feature.fetchweather.data

import com.squareup.moshi.Json

data class HourlyWeather(
    @Json(name = "dt") val timeStamp: Long,
    val temp: Double,
    val weather: List<Weather>
)