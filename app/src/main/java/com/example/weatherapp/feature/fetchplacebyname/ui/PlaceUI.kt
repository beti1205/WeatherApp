package com.example.weatherapp.feature.fetchplacebyname.ui

import android.os.Parcelable
import com.example.weatherapp.feature.fetchplacebyname.data.Place
import com.example.weatherapp.feature.fetchplacebyname.data.Properties
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlaceUI(
    val latitude: Double,
    val longitude: Double,
    val properties: Properties
) : Parcelable

fun List<Place>.toPlaceUI(): List<PlaceUI> {
    return map { place ->
        PlaceUI(
            latitude = place.geometry.coordinates[1],
            longitude = place.geometry.coordinates[0],
            properties = place.properties
        )
    }
}
