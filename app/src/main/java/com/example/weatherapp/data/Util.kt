package com.example.weatherapp.data

import android.widget.ImageView
import com.example.weatherapp.R

fun ImageView.setWeatherImage(iconId: String) {
    setImageResource(
        when (iconId) {
            "01d" -> R.drawable.wi_day_sunny
            "01n" -> R.drawable.wi_night_clear
            "02d" -> R.drawable.wi_day_cloudy
            "02n" -> R.drawable.wi_night_alt_cloudy
            "03d", "03n" -> R.drawable.wi_cloud
            "04d", "04n" -> R.drawable.wi_cloudy
            "09d", "09n" -> R.drawable.wi_showers
            "10d", "10n" -> R.drawable.wi_rain
            "11d", "11n" -> R.drawable.wi_thunderstorm
            "13d", "13n" -> R.drawable.wi_snow
            "50d", "50n" -> R.drawable.wi_fog
            else -> R.drawable.wi_cloud
        }
    )
}