package com.example.weatherapp.feature.fetchweather.domain

import com.example.weatherapp.common.Result
import com.example.weatherapp.common.performRequest
import com.example.weatherapp.feature.fetchcityname.data.AppConfig
import com.example.weatherapp.feature.fetchweather.data.WeatherApiService
import com.example.weatherapp.feature.fetchweather.data.WeatherReport
import javax.inject.Inject

interface FetchWeatherUseCase {

    suspend operator fun invoke(
        latitude: Double,
        longitude: Double,
        units: String
    ): Result<WeatherReport>
}

class FetchWeatherUseCaseImpl @Inject constructor(
    private val apiService: WeatherApiService,
    private val appConfig: AppConfig
): FetchWeatherUseCase {
    override suspend fun invoke(
        latitude: Double,
        longitude: Double,
        units: String
    ): Result<WeatherReport> {
        return performRequest {
            apiService.getWeather(
                latitude.toString(),
                longitude.toString(),
                appConfig.apiKey,
                units
            )
        }
    }
}

