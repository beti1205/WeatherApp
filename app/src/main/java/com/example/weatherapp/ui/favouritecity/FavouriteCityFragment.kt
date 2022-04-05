package com.example.weatherapp.ui.favouritecity

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentFavouriteCityBinding
import com.example.weatherapp.ui.weather.DailyWeatherAdapter

class FavouriteCityFragment: Fragment(R.layout.fragment_favourite_city) {

    private var _binding: FragmentFavouriteCityBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentFavouriteCityBinding.bind(view)

        val favouriteCityAdapter = FavouriteCityAdapter()
        binding.favouriteCityList.adapter = favouriteCityAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}