package com.example.weatherapp.data

data class WeatherReport(
    val current: CurrentWeather,
    val hourly: List<HourlyWeather>,
    val daily: List<DailyWeather>
)