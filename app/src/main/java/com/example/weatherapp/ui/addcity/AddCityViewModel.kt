package com.example.weatherapp.ui.addcity

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.common.Result
import com.example.weatherapp.feature.fetchplacebyname.domain.FetchPlaceByNameUseCase
import com.example.weatherapp.feature.fetchplacebyname.ui.PlaceUI
import com.example.weatherapp.feature.storefavouritecities.domain.AddFavouriteCityUseCase
import com.example.weatherapp.feature.storefavouritecities.domain.AddFavouriteCityUseCaseParams
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddCityViewModel @Inject constructor(
    private val fetchPlaceByNameUseCase: FetchPlaceByNameUseCase,
    private val addFavouriteCityUseCase: AddFavouriteCityUseCase
) : ViewModel() {

    private var fetchJob: Job? = null

    private val _place: MutableLiveData<List<PlaceUI>?> = MutableLiveData()
    val place: LiveData<List<PlaceUI>?> = _place

    private val _emptyState: MutableLiveData<EmptyStateType?> = MutableLiveData()
    val emptyState: LiveData<EmptyStateType?> = _emptyState

    @SuppressLint("NullSafeMutableLiveData")
    fun onCityNameChanged(cityName: String) {
        fetchJob?.cancel()

        val isBelowMinimumLength = cityName.length < 3
        if (isBelowMinimumLength) {
            _emptyState.value = EmptyStateType.SEARCH_CITY
            return
        }

        fetchJob = viewModelScope.launch {
            delay(600)
            val result = fetchPlaceByNameUseCase(
                cityName = cityName
            )
            when (result) {
                is Result.Success -> {
                    val isResponseEmpty = result.data.isEmpty()
                    _place.value = result.data
                    _emptyState.value = if (isResponseEmpty) {
                        EmptyStateType.NO_RESULT
                    } else {
                        null
                    }
                }
                is Result.Error -> _emptyState.value = EmptyStateType.ERROR
            }
        }
    }

    fun addFavouriteCityToDatabase(place: PlaceUI) {
        viewModelScope.launch {
            addFavouriteCityUseCase(
                AddFavouriteCityUseCaseParams(
                    id = place.properties.id,
                    name = place.properties.name,
                    country = place.properties.country,
                    county = place.properties.county,
                    latitude = place.latitude,
                    longitude = place.longitude
                )
            )
        }
    }
}