package com.example.weatherapp.ui.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.network.Result
import com.example.weatherapp.network.WeatherApi
import com.example.weatherapp.data.WeatherReport
import kotlinx.coroutines.launch
import kotlin.Exception

private const val key = "5fe4e1715cf27ea3002f0c66ebda3d51"
private const val units = "metric"
private const val latitudeWarsaw = "52.15"
private const val longitudeWarsaw = "21.00"

class WeatherViewModel : ViewModel() {

    private val _response = MutableLiveData<Result<WeatherReport>>()

    val response: MutableLiveData<Result<WeatherReport>>
        get() = _response


    init {
        getWeatherReport()
    }

    private fun getWeatherReport() {
        viewModelScope.launch {
            try {
                val response = WeatherApi.retrofitService.getWeather(
                    latitudeWarsaw,
                    longitudeWarsaw,
                    key,
                    units
                )
                _response.value = Result.Success(response)
            } catch (e: Exception) {
                _response.value = Result.Error(e)
            }
        }
    }


}

//inline fun <reified T : Any> performRequest(block: () -> T): Result<T> {
//    return try {
//        Result.Success(block())
//    } catch (ex: Exception) {
//        Result.Error(ex)
//    }
//}

