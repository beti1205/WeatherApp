package com.example.weatherapp.ui.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.common.Result
import com.example.weatherapp.feature.fetchcityname.data.AppConfig
import com.example.weatherapp.feature.fetchcityname.domain.FetchCityNameUseCase
import com.example.weatherapp.feature.fetchweather.data.WeatherReport
import com.example.weatherapp.feature.fetchweather.data.WeatherApiService
import com.example.weatherapp.feature.fetchweather.domain.FetchWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.Exception

private const val units = "metric"
private const val latitudeWarsaw = 52.15
private const val longitudeWarsaw = 21.00
private const val limit = 200

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val fetchWeatherUseCase: FetchWeatherUseCase,
    private val fetchCityNameUseCase: FetchCityNameUseCase,
    private val config: AppConfig
) : ViewModel() {

    private val _weatherReport = MutableLiveData<Result<WeatherReport>>()
    val weatherReport: LiveData<Result<WeatherReport>> = _weatherReport

    private val _cityName = MutableLiveData<Result<String?>>()
    val cityName: LiveData<Result<String?>> = _cityName

    fun fetchWeatherReport(latitude: Double = latitudeWarsaw, longitude: Double = longitudeWarsaw) {
        viewModelScope.launch {
       _weatherReport.value = fetchWeatherUseCase(latitude, longitude, units)
        }
    }

    fun fetchCityName(latitude: Double = latitudeWarsaw, longitude: Double = longitudeWarsaw) {
        viewModelScope.launch {
            _cityName.value = fetchCityNameUseCase(latitude, longitude, limit)
        }
    }
}

