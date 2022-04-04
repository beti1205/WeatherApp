package com.example.weatherapp.feature.fetchweather.data

data class WeatherReport(
    val current: CurrentWeather,
    val hourly: List<HourlyWeather>,
    val daily: List<DailyWeather>
)