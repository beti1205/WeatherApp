package com.example.weatherapp.feature.fetchplacebycoorinates.domain

import com.example.weatherapp.common.AppConfig
import com.example.weatherapp.common.LocaleProvider
import com.example.weatherapp.common.Result
import com.example.weatherapp.common.performRequest
import com.example.weatherapp.feature.fetchplacebycoorinates.data.ReverseGeocodingService
import java.util.*
import javax.inject.Inject

interface FetchPlaceByCoordinatesUseCase {

    suspend operator fun invoke(
        latitude: Double,
        longitude: Double
    ): Result<String?>
}

class FetchPlaceByCoordinatesUseCaseImpl @Inject constructor(
    private val localeProvider: LocaleProvider,
    private val geocodingApiService: ReverseGeocodingService,
    private val appConfig: AppConfig
) : FetchPlaceByCoordinatesUseCase {

    override suspend fun invoke(
        latitude: Double,
        longitude: Double
    ): Result<String?> {
        return performRequest {
            val results = geocodingApiService.getPlaceByCoordinates(
                latitude,
                longitude,
                appConfig.limit,
                appConfig.apiKey
            )
            val localNames = results.first().localNames
            localNames[localeProvider.getLocale().language] ?: localNames[Locale.ENGLISH.language]
        }
    }
}
