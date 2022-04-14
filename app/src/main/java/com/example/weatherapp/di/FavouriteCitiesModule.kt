package com.example.weatherapp.di

import com.example.weatherapp.common.Database
import com.example.weatherapp.feature.storefavouritecities.data.FavouriteCityDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class FavouriteCitiesModule {

    @Provides
    fun provideFavouriteCityDao(database: Database): FavouriteCityDao {
        return database.favouriteCityDao()
    }
}