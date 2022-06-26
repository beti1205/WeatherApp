package com.example.weatherapp.di

import com.example.weatherapp.common.Database
import com.example.weatherapp.feature.storefavouritecities.data.FavouriteCityDao
import com.example.weatherapp.feature.storefavouritecities.data.FavouriteCityRepository
import com.example.weatherapp.feature.storefavouritecities.data.FavouriteCityRepositoryImpl
import com.example.weatherapp.feature.storefavouritecities.domain.*
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class FavouriteCitiesModule {

    companion object {

        @Provides
        fun provideFavouriteCityDao(database: Database): FavouriteCityDao = database
            .favouriteCityDao()
    }

    @Binds
    abstract fun bindFavouriteCityRepository(
        favouriteCityRepositoryImpl: FavouriteCityRepositoryImpl
    ): FavouriteCityRepository

    @Binds
    abstract fun bindFetchFavouriteCitiesUseCase(
        fetchFavouriteCitiesUseCaseImpl: FetchFavouriteCitiesUseCaseImpl
    ): FetchFavouriteCitiesUseCase

    @Binds
    abstract fun bindDeleteFavouriteCityUseCase(
        deleteFavouriteCityUseCaseImpl: DeleteFavouriteCityUseCaseImpl
    ): DeleteFavouriteCityUseCase

    @Binds
    abstract fun bindAddFavouriteCityUseCase(
        addFavouriteCityUseCaseImpl: AddFavouriteCityUseCaseImpl
    ): AddFavouriteCityUseCase
}