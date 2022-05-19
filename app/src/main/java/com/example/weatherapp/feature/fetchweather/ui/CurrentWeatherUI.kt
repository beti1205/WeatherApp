package com.example.weatherapp.feature.fetchweather.ui

import com.example.weatherapp.feature.fetchweather.data.CurrentWeather
import com.example.weatherapp.feature.fetchweather.data.Weather
import com.example.weatherapp.feature.fetchweather.data.WeatherReport
import com.example.weatherapp.utils.formattedTime
import com.example.weatherapp.utils.toZoneDateTime

data class CurrentWeatherUI(
    val temp: Double,
    val weather: List<Weather>,
    val sunrise: String,
    val sunset: String,
    val pressure: Int,
    val humidity: Int,
    val visibility: Int,
    val windSpeed: Double
)

fun CurrentWeather.toCurrentWeatherUI(weatherReport: WeatherReport): CurrentWeatherUI {
    val sunriseTime = sunrise.toZoneDateTime(weatherReport.timezone).formattedTime
    val sunsetTime = sunset.toZoneDateTime(weatherReport.timezone).formattedTime

    return CurrentWeatherUI(
        temp = temp,
        weather = weather,
        sunrise = sunriseTime,
        sunset = sunsetTime,
        pressure = pressure,
        humidity = humidity,
        visibility = visibility,
        windSpeed = windSpeed
    )
}


