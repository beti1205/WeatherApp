package com.example.weatherapp.feature.fetchplacebycoorinates.data

import com.squareup.moshi.Json

data class Place(
    @Json(name = "local_names")
    val localNames: Map<String, String>,
    val lat: Double,
    val lon: Double
)