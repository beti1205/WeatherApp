package com.example.weatherapp.feature.fetchcityname.domain

import android.content.Context
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import com.example.weatherapp.common.Result
import com.example.weatherapp.common.performRequest
import com.example.weatherapp.feature.fetchcityname.data.AppConfig
import com.example.weatherapp.feature.fetchcityname.data.ReverseGeocodingService
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*
import javax.inject.Inject

interface FetchCityNameUseCase {

    suspend operator fun invoke(
        latitude: Double,
        longitude: Double,
        limit: Int
    ): Result<String?>

}

class FetchCityNameUseCaseImpl @Inject constructor(
    @ApplicationContext context: Context,
    private val geocodingApiService: ReverseGeocodingService,
    private val appConfig: AppConfig
) : FetchCityNameUseCase {

    private val current = context.resources.configuration

    override suspend fun invoke(
        latitude: Double,
        longitude: Double,
        limit: Int
    ): Result<String?> {
        return performRequest {
            val results = geocodingApiService.getGeocoding(
                latitude,
                longitude,
                limit,
                appConfig.apiKey
            )
            val map = results.first().localName
            val locale = getCurrentLocale()
            map[locale.language] ?: map[Locale.ENGLISH.language]
        }
    }

    private fun getCurrentLocale(): Locale {
        return if (VERSION.SDK_INT >= VERSION_CODES.N) {
            current.locales[0]
        } else {
            current.locale
        }
    }
}
