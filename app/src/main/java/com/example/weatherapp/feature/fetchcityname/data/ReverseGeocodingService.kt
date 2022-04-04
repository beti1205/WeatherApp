package com.example.weatherapp.feature.fetchcityname.data

import retrofit2.http.GET
import retrofit2.http.Query

interface ReverseGeocodingService {
    @GET("reverse")
    suspend fun getGeocoding(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("limit") limit: Int,
        @Query("appid") apiKey: String,
    ): List<Place>
}