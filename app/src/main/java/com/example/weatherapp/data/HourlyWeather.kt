package com.example.weatherapp.data

import com.squareup.moshi.Json
import java.util.*

data class HourlyWeather(
    @Json(name = "dt") val time: Date,
    val temp: Double,
    val weather: List<Weather>
)