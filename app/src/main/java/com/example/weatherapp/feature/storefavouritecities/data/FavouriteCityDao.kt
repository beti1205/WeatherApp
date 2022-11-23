package com.example.weatherapp.feature.storefavouritecities.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteCityDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favouriteCity: FavouriteCity)

    @Query("SELECT * FROM favourite_cities_table")
    fun getAllFavouriteCities(): Flow<List<FavouriteCity>>

    @Delete
    suspend fun delete(favouriteCity: FavouriteCity)
}
