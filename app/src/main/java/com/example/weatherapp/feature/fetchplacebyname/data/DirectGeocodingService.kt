package com.example.weatherapp.feature.fetchplacebyname.data

import retrofit2.http.GET
import retrofit2.http.Query

interface DirectGeocodingService {
    @GET("api")
    suspend fun getPlaceByName(
        @Query("q") cityName: String
    ): DirectGeocodingResponse
}