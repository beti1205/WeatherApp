package com.example.weatherapp.feature.fetchweather.ui

import com.example.weatherapp.feature.fetchweather.data.HourlyWeather
import com.example.weatherapp.feature.fetchweather.data.Weather
import com.example.weatherapp.feature.fetchweather.data.WeatherReport
import com.example.weatherapp.utils.formattedTimeShort
import com.example.weatherapp.utils.toZoneDateTime

data class HourlyWeatherUI(
    val timeStamp: Long,
    val time: String,
    val temp: Double,
    val weather: List<Weather>
)

fun List<HourlyWeather>.toHourlyWeatherUI(weatherReport: WeatherReport): List<HourlyWeatherUI> {
    return map { weather ->
        val time = weather.timeStamp.toZoneDateTime(weatherReport.timezone).formattedTimeShort

        HourlyWeatherUI(
            timeStamp = weather.timeStamp,
            time = time,
            temp = weather.temp,
            weather = weather.weather
        )
    }
}

