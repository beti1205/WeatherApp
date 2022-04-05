package com.example.weatherapp.feature.fetchplacebycoorinates.domain

import android.content.Context
import com.example.weatherapp.common.Result
import com.example.weatherapp.common.performRequest
import com.example.weatherapp.common.AppConfig
import com.example.weatherapp.feature.fetchplacebycoorinates.data.ReverseGeocodingService
import com.example.weatherapp.utils.getCurrentLocale
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*
import javax.inject.Inject

interface FetchPlaceByCoordinatesUseCase {

    suspend operator fun invoke(
        latitude: Double,
        longitude: Double
    ): Result<String?>

}

class FetchPlaceByCoordinatesUseCaseImpl @Inject constructor(
    @ApplicationContext context: Context,
    private val geocodingApiService: ReverseGeocodingService,
    private val appConfig: AppConfig
) : FetchPlaceByCoordinatesUseCase {

    private val locale = context.getCurrentLocale()

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
            localNames[locale.language] ?: localNames[Locale.ENGLISH.language]
        }
    }
}
