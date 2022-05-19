package com.example.weatherapp.feature.storefavouritecities.domain

import com.example.weatherapp.feature.storefavouritecities.data.FavouriteCity
import com.example.weatherapp.feature.storefavouritecities.data.FavouriteCityRepository
import javax.inject.Inject

interface DeleteFavouriteCityUseCase {

    suspend operator fun invoke(favouriteCity: FavouriteCity)
}

class DeleteFavouriteCityUseCaseImpl @Inject constructor(
    private val repository: FavouriteCityRepository
) : DeleteFavouriteCityUseCase {

    override suspend fun invoke(
        favouriteCity: FavouriteCity
    ): Unit = repository.deleteCity(favouriteCity)
}
