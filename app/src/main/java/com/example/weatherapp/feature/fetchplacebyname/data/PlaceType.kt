package com.example.weatherapp.feature.fetchplacebyname.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
enum class PlaceType {
    @Json(name = "city")
    CITY,
    UNKNOWN
}
