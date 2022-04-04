package com.example.weatherapp.di

import com.example.weatherapp.feature.fetchcityname.data.AppConfig
import com.example.weatherapp.feature.fetchcityname.data.ReverseGeocodingService
import com.example.weatherapp.feature.fetchcityname.domain.FetchCityNameUseCase
import com.example.weatherapp.feature.fetchcityname.domain.FetchCityNameUseCaseImpl
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
    abstract fun bindCityNameInterface(
        fetchCityNameUseCase: FetchCityNameUseCaseImpl
    ): FetchCityNameUseCase
}