package com.example.weatherapp.ui.weather

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.common.Result
import com.example.weatherapp.feature.fetchplacebycoorinates.domain.FetchPlaceByCoordinatesUseCase
import com.example.weatherapp.feature.fetchweather.data.WeatherReport
import com.example.weatherapp.feature.fetchweather.domain.FetchWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val latitudeWarsaw = 52.15
private const val longitudeWarsaw = 21.00

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val fetchWeatherUseCase: FetchWeatherUseCase,
    private val fetchPlaceByCoordinatesUseCase: FetchPlaceByCoordinatesUseCase
) : ViewModel() {

    private val _weatherReport = MutableLiveData<Result<WeatherReport>>()
    val weatherReport: LiveData<Result<WeatherReport>> = _weatherReport

    private val _cityName = MutableLiveData<Result<String?>>()
    val cityName: LiveData<Result<String?>> = _cityName

    @SuppressLint("NullSafeMutableLiveData")
    fun fetchWeatherReport(latitude: Double = latitudeWarsaw, longitude: Double = longitudeWarsaw) {
        viewModelScope.launch {
            _weatherReport.value = fetchWeatherUseCase(latitude, longitude)
        }
    }

    @SuppressLint("NullSafeMutableLiveData")
    fun fetchCityName(latitude: Double = latitudeWarsaw, longitude: Double = longitudeWarsaw) {
        viewModelScope.launch {
            _cityName.value = fetchPlaceByCoordinatesUseCase(latitude, longitude)
        }
    }
}

