package com.example.weatherapp.feature.storefavouritecities.domain

import com.example.weatherapp.feature.storefavouritecities.data.FavouriteCity
import com.example.weatherapp.feature.storefavouritecities.data.FavouriteCityRepository
import javax.inject.Inject

data class AddFavouriteCityUseCaseParams(
    val id: Long,
    val name: String?,
    val country: String,
    val county: String?,
    val latitude: Double,
    val longitude: Double
)

interface AddFavouriteCityUseCase {

    suspend operator fun invoke(
        params: AddFavouriteCityUseCaseParams
    )
}

class AddFavouriteCityUseCaseImpl @Inject constructor(
    private val repository: FavouriteCityRepository
) : AddFavouriteCityUseCase {

    override suspend fun invoke(
        params: AddFavouriteCityUseCaseParams
    ): Unit = repository.insert(
        FavouriteCity(
            id = params.id,
            name = params.name,
            country = params.country,
            county = params.county,
            latitude = params.latitude,
            longitude = params.longitude
        )
    )
}
