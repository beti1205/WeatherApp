package com.example.weatherapp.feature.fetchweather.ui

import com.example.weatherapp.feature.fetchweather.data.DailyWeather
import com.example.weatherapp.feature.fetchweather.data.Temperature
import com.example.weatherapp.feature.fetchweather.data.Weather
import com.example.weatherapp.feature.fetchweather.data.WeatherReport
import com.example.weatherapp.utils.formattedDate
import com.example.weatherapp.utils.formattedDayName
import com.example.weatherapp.utils.toZoneDateTime

data class DailyWeatherUI(
    val timeStamp: Long,
    val date: String,
    val day: String,
    val weather: List<Weather>,
    val temp: Temperature
)

fun List<DailyWeather>.toDailyWeatherUI(weatherReport: WeatherReport): List<DailyWeatherUI> {
    return map { weather ->
        val date = weather.timeStamp.toZoneDateTime(weatherReport.timezone)
        DailyWeatherUI(
            timeStamp = weather.timeStamp,
            date = date.formattedDate,
            day = date.formattedDayName,
            weather = weather.weather,
            temp = weather.temp
        )
    }
}
