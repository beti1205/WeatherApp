package com.example.weatherapp.di

import com.example.weatherapp.feature.fetchweather.data.WeatherApiService
import com.example.weatherapp.feature.fetchweather.domain.FetchWeatherUseCase
import com.example.weatherapp.feature.fetchweather.domain.FetchWeatherUseCaseImpl
import com.squareup.moshi.Moshi
import dagger.Binds
import com.example.weatherapp.common.AppConfig
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
abstract class WeatherModule {

    companion object {

        @Provides
        fun provideWeatherApiService(
            client: OkHttpClient,
            moshi: Moshi,
            appConfig: AppConfig
        ): WeatherApiService = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(appConfig.weatherBaseUrl)
            .client(client)
            .build()
            .create()
    }

    @Binds
    abstract fun bindFetchWeatherUseCase(
        fetchWeatherUseCase: FetchWeatherUseCaseImpl
    ): FetchWeatherUseCase
}