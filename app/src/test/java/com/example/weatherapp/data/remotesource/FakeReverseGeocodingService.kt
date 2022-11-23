package com.example.weatherapp.data.remotesource

import com.example.weatherapp.feature.fetchplacebycoorinates.data.Place
import com.example.weatherapp.feature.fetchplacebycoorinates.data.ReverseGeocodingService
import java.io.IOException

class FakeReverseGeocodingService(
    var places: List<Place> = listOf()
) : ReverseGeocodingService {

    var isSuccessful: Boolean = true

    override suspend fun getPlaceByCoordinates(
        latitude: Double,
        longitude: Double,
        limit: Int,
        apiKey: String
    ): List<Place> {
        if (isSuccessful) {
            return places
        } else {
            throw IOException()
        }
    }
}
