package com.example.weatherapp.feature.storefavouritecities.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "favourite_cities_table")
@Parcelize
data class FavouriteCity(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val name: String?,
    val country: String,
    val county: String?,
    val latitude: Double,
    val longitude: Double
): Parcelable