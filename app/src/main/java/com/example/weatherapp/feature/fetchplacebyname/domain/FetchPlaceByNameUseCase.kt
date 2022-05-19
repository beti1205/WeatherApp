package com.example.weatherapp.feature.fetchplacebyname.domain

import com.example.weatherapp.common.Result
import com.example.weatherapp.common.performRequest
import com.example.weatherapp.feature.fetchplacebyname.data.DirectGeocodingService
import com.example.weatherapp.feature.fetchplacebyname.data.PlaceType
import com.example.weatherapp.feature.fetchplacebyname.ui.PlaceUI
import com.example.weatherapp.feature.fetchplacebyname.ui.toPlaceUI
import javax.inject.Inject

interface FetchPlaceByNameUseCase {

    suspend operator fun invoke(
        cityName: String
    ): Result<List<PlaceUI>>
}

class FetchPlaceByNameUseCaseImpl @Inject constructor(
    private val directGeocodingService: DirectGeocodingService,
) : FetchPlaceByNameUseCase {

    override suspend fun invoke(
        cityName: String
    ): Result<List<PlaceUI>> {
        val result = performRequest {
            directGeocodingService.getPlaceByName(cityName)
        }
        return when (result) {
            is Result.Success -> Result.Success(
                result.data.features
                    .toPlaceUI()
                    .filter { it.properties.type == PlaceType.CITY }
            )
            is Result.Error -> result
        }
    }
}

