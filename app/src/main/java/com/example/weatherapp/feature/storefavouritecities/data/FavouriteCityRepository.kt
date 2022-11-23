package com.example.weatherapp.feature.storefavouritecities.data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface FavouriteCityRepository {

    suspend fun insert(favouriteCity: FavouriteCity)

    fun getCities(): Flow<List<FavouriteCity>>

    suspend fun deleteCity(favouriteCity: FavouriteCity)
}

class FavouriteCityRepositoryImpl @Inject constructor(
    private val favouriteCityDao: FavouriteCityDao
) : FavouriteCityRepository {

    override suspend fun insert(favouriteCity: FavouriteCity) {
        favouriteCityDao.insert(favouriteCity)
    }

    override fun getCities(): Flow<List<FavouriteCity>> {
        return favouriteCityDao.getAllFavouriteCities()
    }

    override suspend fun deleteCity(favouriteCity: FavouriteCity) {
        favouriteCityDao.delete(favouriteCity)
    }
}
