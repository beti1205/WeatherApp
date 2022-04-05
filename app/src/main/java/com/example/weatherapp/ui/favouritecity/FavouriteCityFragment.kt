package com.example.weatherapp.ui.favouritecity

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FavouriteCityFragmentBinding

class FavouriteCityFragment : Fragment(R.layout.favourite_city_fragment) {

    private var _binding: FavouriteCityFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FavouriteCityFragmentBinding.bind(view)

        val favouriteCityAdapter = FavouriteCityAdapter()
        binding.favouriteCityList.adapter = favouriteCityAdapter

        binding.addNewCityButton.setOnClickListener {
            view.findNavController().navigate(
                FavouriteCityFragmentDirections.actionFavouriteCityFragmentToAddCityFragment()
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}