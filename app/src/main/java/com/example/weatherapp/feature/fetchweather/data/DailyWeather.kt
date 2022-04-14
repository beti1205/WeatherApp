package com.example.weatherapp.feature.fetchweather.data

import com.squareup.moshi.Json

data class DailyWeather(
    @Json(name = "dt") val timeStamp: Long,
    val weather: List<Weather>,
    val temp: Temperature
)

