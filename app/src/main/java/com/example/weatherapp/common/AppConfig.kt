package com.example.weatherapp.common

data class AppConfig(
    val weatherBaseUrl: String,
    val reverseGeocodingBaseUrl: String,
    val geocodingUrl: String,
    val apiKey: String,
    val limit: Int,
    val units: String
)

