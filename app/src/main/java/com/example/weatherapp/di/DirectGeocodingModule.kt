package com.example.weatherapp.di

import com.example.weatherapp.feature.fetchplacebyname.data.DirectGeocodingService
import com.example.weatherapp.feature.fetchplacebyname.domain.FetchPlaceByNameUseCase
import com.example.weatherapp.feature.fetchplacebyname.domain.FetchPlaceByNameUseCaseImpl
import com.example.weatherapp.common.AppConfig
import com.squareup.moshi.Moshi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

@Module
@InstallIn(SingletonComponent::class)
abstract class DirectGeocodingModule {

    companion object {

        @Provides
        fun provideDirectGeocodingService(
            client: OkHttpClient,
            moshi: Moshi,
            appConfig: AppConfig
        ): DirectGeocodingService {
            return Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .baseUrl(appConfig.geocodingUrl)
                .client(client)
                .build()
                .create()
        }
    }

    @Binds
    abstract fun bindPlaceByNameInterface(
        fetchPlaceByNameUseCase: FetchPlaceByNameUseCaseImpl
    ): FetchPlaceByNameUseCase
}