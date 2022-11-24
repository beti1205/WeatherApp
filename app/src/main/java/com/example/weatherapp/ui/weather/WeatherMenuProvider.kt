package com.example.weatherapp.ui.weather

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.view.MenuProvider
import com.example.weatherapp.R

class WeatherMenuProvider(private val onCheckCitiesCLicked: () -> Unit) : MenuProvider {
    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.weather_fragment_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.check_favourite_cities_item -> onCheckCitiesCLicked()
        }
        return true
    }
}
