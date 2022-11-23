package com.example.weatherapp.di

import com.example.weatherapp.common.Database
import com.example.weatherapp.feature.storefavouritecities.data.FavouriteCityDao
import com.example.weatherapp.feature.storefavouritecities.data.FavouriteCityRepository
import com.example.weatherapp.feature.storefavouritecities.data.FavouriteCityRepositoryImpl
import com.example.weatherapp.feature.storefavouritecities.domain.AddFavouriteCityUseCase
import com.example.weatherapp.feature.storefavouritecities.domain.AddFavouriteCityUseCaseImpl
import com.example.weatherapp.feature.storefavouritecities.domain.DeleteFavouriteCityUseCase
import com.example.weatherapp.feature.storefavouritecities.domain.DeleteFavouriteCityUseCaseImpl
import com.example.weatherapp.feature.storefavouritecities.domain.FetchFavouriteCitiesUseCase
import com.example.weatherapp.feature.storefavouritecities.domain.FetchFavouriteCitiesUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface FavouriteCitiesModule {

    companion object {

        @Provides
        fun provideFavouriteCityDao(database: Database): FavouriteCityDao = database
            .favouriteCityDao()
    }

    @Binds
    fun bindFavouriteCityRepository(
        favouriteCityRepositoryImpl: FavouriteCityRepositoryImpl
    ): FavouriteCityRepository

    @Binds
    fun bindFetchFavouriteCitiesUseCase(
        fetchFavouriteCitiesUseCaseImpl: FetchFavouriteCitiesUseCaseImpl
    ): FetchFavouriteCitiesUseCase

    @Binds
    fun bindDeleteFavouriteCityUseCase(
        deleteFavouriteCityUseCaseImpl: DeleteFavouriteCityUseCaseImpl
    ): DeleteFavouriteCityUseCase

    @Binds
    fun bindAddFavouriteCityUseCase(
        addFavouriteCityUseCaseImpl: AddFavouriteCityUseCaseImpl
    ): AddFavouriteCityUseCase
}
