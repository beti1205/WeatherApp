package com.example.weatherapp.common

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherapp.feature.storefavouritecities.data.FavouriteCity
import com.example.weatherapp.feature.storefavouritecities.data.FavouriteCityDao

@Database(entities = [FavouriteCity::class], version = 1)
abstract class Database : RoomDatabase(){

    abstract fun favouriteCityDao(): FavouriteCityDao
}