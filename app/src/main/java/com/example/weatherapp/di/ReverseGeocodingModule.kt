package com.example.weatherapp.di

import com.example.weatherapp.common.AppConfig
import com.example.weatherapp.common.LocaleProvider
import com.example.weatherapp.common.LocaleProviderImpl
import com.example.weatherapp.feature.fetchplacebycoorinates.data.ReverseGeocodingService
import com.example.weatherapp.feature.fetchplacebycoorinates.domain.FetchPlaceByCoordinatesUseCase
import com.example.weatherapp.feature.fetchplacebycoorinates.domain.FetchPlaceByCoordinatesUseCaseImpl
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
abstract class ReverseGeocodingModule {

    companion object {

        @Provides
        fun provideReverseGeocodingService(
            client: OkHttpClient,
            moshi: Moshi,
            appConfig: AppConfig
        ): ReverseGeocodingService {
            return Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .baseUrl(appConfig.reverseGeocodingBaseUrl)
                .client(client)
                .build()
                .create()
        }
    }

    @Binds
    abstract fun bindFetchPlaceByCoordinatesUseCase(
        fetchPlaceByCoordinatesUseCase: FetchPlaceByCoordinatesUseCaseImpl
    ): FetchPlaceByCoordinatesUseCase

    @Binds
    abstract fun bindLocaleProvider(
        localeProvider: LocaleProviderImpl
    ): LocaleProvider
}