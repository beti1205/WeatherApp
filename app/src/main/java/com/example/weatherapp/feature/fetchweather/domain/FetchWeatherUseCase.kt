package com.example.weatherapp.feature.fetchweather.domain

import com.example.weatherapp.common.AppConfig
import com.example.weatherapp.common.Result
import com.example.weatherapp.common.performRequest
import com.example.weatherapp.feature.fetchweather.data.WeatherApiService
import com.example.weatherapp.feature.fetchweather.ui.WeatherReportUI
import com.example.weatherapp.feature.fetchweather.ui.toWeatherReportUI
import javax.inject.Inject

interface FetchWeatherUseCase {

    suspend operator fun invoke(
        latitude: Double,
        longitude: Double
    ): Result<WeatherReportUI>
}

class FetchWeatherUseCaseImpl @Inject constructor(
    private val apiService: WeatherApiService,
    private val appConfig: AppConfig
) : FetchWeatherUseCase {

    override suspend fun invoke(
        latitude: Double,
        longitude: Double
    ): Result<WeatherReportUI> {
         val result = performRequest {
            apiService.getWeather(
                latitude.toString(),
                longitude.toString(),
                appConfig.apiKey,
                appConfig.units
            )
        }
        return when(result){
            is Result.Error -> result
            is Result.Success -> Result.Success(
              result.data.toWeatherReportUI()
            )
        }
    }
}

