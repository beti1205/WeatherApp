package com.example.weatherapp.feature.fetchcityname.data

import com.squareup.moshi.Json

data class Place(
    @Json(name = "local_names") val localName: Map<String, String>,
    val lat: Double,
    val lon: Double
)