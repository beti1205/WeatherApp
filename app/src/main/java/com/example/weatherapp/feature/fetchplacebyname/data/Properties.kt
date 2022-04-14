package com.example.weatherapp.feature.fetchplacebyname.data

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Properties(
    @Json(name = "osm_id")
    val id: Long,
    val name: String?,
    val country: String,
    val type: PlaceType?,
    val county: String?
): Parcelable
