package com.example.weatherapp.feature.storefavouritecities.domain

import com.example.weatherapp.feature.storefavouritecities.data.FavouriteCity
import com.example.weatherapp.feature.storefavouritecities.data.FavouriteCityRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface FetchFavouriteCitiesUseCase {

    operator fun invoke(): Flow<List<FavouriteCity>>
}

class FetchFavouriteCitiesUseCaseImpl @Inject constructor(
    private val repository: FavouriteCityRepository
) : FetchFavouriteCitiesUseCase {

    override fun invoke(): Flow<List<FavouriteCity>> = repository.getCities()
}
