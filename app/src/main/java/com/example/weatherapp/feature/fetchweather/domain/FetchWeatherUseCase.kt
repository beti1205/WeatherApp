package com.example.weatherapp.feature.fetchweather.domain

import com.example.weatherapp.common.AppConfig
import com.example.weatherapp.common.Result
import com.example.weatherapp.common.performRequest
import com.example.weatherapp.feature.fetchweather.data.WeatherApiService
import com.example.weatherapp.feature.fetchweather.data.WeatherReport
import javax.inject.Inject

interface FetchWeatherUseCase {

    suspend operator fun invoke(
        latitude: Double,
        longitude: Double
    ): Result<WeatherReport>
}

class FetchWeatherUseCaseImpl @Inject constructor(
    private val apiService: WeatherApiService,
    private val appConfig: AppConfig
) : FetchWeatherUseCase {
    override suspend fun invoke(
        latitude: Double,
        longitude: Double
    ): Result<WeatherReport> {
        return performRequest {
            apiService.getWeather(
                latitude.toString(),
                longitude.toString(),
                appConfig.apiKey,
                appConfig.units
            )
        }
    }
}

