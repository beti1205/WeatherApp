package com.example.weatherapp.ui.favouritecity

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.feature.storefavouritecities.data.FavouriteCity
import com.example.weatherapp.feature.storefavouritecities.domain.DeleteFavouriteCityUseCase
import com.example.weatherapp.feature.storefavouritecities.domain.FetchFavouriteCitiesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteCityViewModel @Inject constructor(
    private val deleteFavouriteCityUseCase: DeleteFavouriteCityUseCase,
    fetchFavouriteCitiesUseCase: FetchFavouriteCitiesUseCase
) : ViewModel() {

    val cities: LiveData<List<FavouriteCity>> = fetchFavouriteCitiesUseCase().asLiveData()

    fun deleteFavouriteCityFromDatabase(favouriteCity: FavouriteCity) {
        viewModelScope.launch {
            deleteFavouriteCityUseCase(favouriteCity)
        }
    }
}