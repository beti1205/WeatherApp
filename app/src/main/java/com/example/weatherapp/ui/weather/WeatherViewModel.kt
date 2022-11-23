package com.example.weatherapp.ui.weather

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.common.Result
import com.example.weatherapp.feature.fetchplacebycoorinates.domain.FetchPlaceByCoordinatesUseCase
import com.example.weatherapp.feature.fetchweather.domain.FetchWeatherUseCase
import com.example.weatherapp.feature.fetchweather.ui.WeatherReportUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val LATITUDE_WARSAW = 52.15
private const val LONGITUDE_WARSAW = 21.00

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val fetchWeatherUseCase: FetchWeatherUseCase,
    private val fetchPlaceByCoordinatesUseCase: FetchPlaceByCoordinatesUseCase
) : ViewModel() {

    private val _weatherReport = MutableLiveData<Result<WeatherReportUI>?>()
    val weatherReport: LiveData<Result<WeatherReportUI>?> = _weatherReport

    private val _cityName = MutableLiveData<String?>()
    val cityName: LiveData<String?> = _cityName

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading

    @SuppressLint("NullSafeMutableLiveData")
    fun fetchWeatherReport(
        latitude: Double? = null,
        longitude: Double? = null,
        force: Boolean = false
    ) {
        if (!force && _weatherReport.value != null) {
            return
        }

        viewModelScope.launch {
            _weatherReport.value = fetchWeatherUseCase(
                latitude = latitude ?: LATITUDE_WARSAW,
                longitude = longitude ?: LONGITUDE_WARSAW
            )
            _isLoading.value = false
        }
    }

    @SuppressLint("NullSafeMutableLiveData")
    fun fetchCityName(latitude: Double? = null, longitude: Double? = null) {
        if (_cityName.value != null) {
            return
        }

        viewModelScope.launch {
            val result = fetchPlaceByCoordinatesUseCase(
                latitude = latitude ?: LATITUDE_WARSAW,
                longitude = longitude ?: LONGITUDE_WARSAW
            )
            _cityName.value = when (result) {
                is Result.Error -> null
                is Result.Success -> result.data
            }
        }
    }

    fun setCityName(name: String?) {
        _cityName.value = name
    }
}
