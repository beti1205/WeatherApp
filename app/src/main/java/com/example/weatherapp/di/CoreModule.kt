package com.example.weatherapp.di

import com.example.weatherapp.BuildConfig
import com.example.weatherapp.common.AppConfig
import com.example.weatherapp.common.adapter.UnixTimestampDateJsonAdapter
import com.example.weatherapp.feature.fetchplacebyname.data.PlaceType
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.EnumJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

    @Provides
    fun provideAppConfig(): AppConfig = AppConfig(
        weatherBaseUrl = "https://api.openweathermap.org/data/2.5/",
        reverseGeocodingBaseUrl = "https://api.openweathermap.org/geo/1.0/",
        geocodingUrl = "https://photon.komoot.io/",
        apiKey = BuildConfig.API_KEY,
        limit = 200,
        units = "metric"
    )

    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .add(UnixTimestampDateJsonAdapter())
        .add(
            PlaceType::class.java,
            EnumJsonAdapter.create(PlaceType::class.java).withUnknownFallback(PlaceType.UNKNOWN)
        )
        .build()

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder().addInterceptor(
            interceptor
        ).build()
}
