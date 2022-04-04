package com.example.weatherapp.feature.fetchweather.data

import com.squareup.moshi.Json
import java.util.*

data class DailyWeather(
    @Json(name = "dt") val time: Date,
    val weather: List<Weather>,
    val temp: Temperature
)

