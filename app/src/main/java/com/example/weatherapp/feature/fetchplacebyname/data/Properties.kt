package com.example.weatherapp.feature.fetchplacebyname.data

data class Properties(
    val name: String?,
    val country: String,
    val type: PlaceType?,
    val county: String?
)
