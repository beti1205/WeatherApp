package com.example.weatherapp.feature.fetchplacebycoorinates.data

import retrofit2.http.GET
import retrofit2.http.Query

interface ReverseGeocodingService {
    @GET("reverse")
    suspend fun getPlaceByCoordinates(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("limit") limit: Int,
        @Query("appid") apiKey: String,
    ): List<Place>
}