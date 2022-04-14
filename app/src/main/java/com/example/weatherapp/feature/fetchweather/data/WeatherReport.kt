package com.example.weatherapp.feature.fetchweather.data

import com.squareup.moshi.Json

data class WeatherReport(
    val timezone: String,
    val current: CurrentWeather,
    val hourly: List<HourlyWeather>,
    val daily: List<DailyWeather>
)