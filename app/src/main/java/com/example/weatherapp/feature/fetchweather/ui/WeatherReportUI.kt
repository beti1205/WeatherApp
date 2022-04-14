package com.example.weatherapp.feature.fetchweather.ui

import com.example.weatherapp.feature.fetchweather.data.WeatherReport

data class WeatherReportUI(
    val current: CurrentWeatherUI,
    val hourly: List<HourlyWeatherUI>,
    val daily: List<DailyWeatherUI>
)

fun WeatherReport.toWeatherReportUI(): WeatherReportUI {
    return WeatherReportUI(
        current = current.toCurrentWeatherUI(this),
        hourly = hourly.toHourlyWeatherUI(this),
        daily = daily.toDailyWeatherUI(this)
    )
}