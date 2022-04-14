package com.example.weatherapp.di

import com.example.weatherapp.feature.storefavouritecities.data.FavouriteCityRepository
import com.example.weatherapp.feature.storefavouritecities.data.FavouriteCityRepositoryImpl
import com.example.weatherapp.feature.storefavouritecities.domain.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class FavouriteCityRepositoryModule {

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