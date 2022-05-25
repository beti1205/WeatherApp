package com.example.weatherapp.ui.favouritecity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FavouriteCityFragmentBinding
import com.example.weatherapp.feature.fetchplacebyname.ui.PlaceUI
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteCityFragment : Fragment(R.layout.favourite_city_fragment) {

    private val viewModel: FavouriteCityViewModel by viewModels()
    private var _binding: FavouriteCityFragmentBinding? = null
    private val binding get() = _binding!!

    companion object{
        const val PLACE = "place"
        const val CITY_LOCATION = "cityLocation"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FavouriteCityFragmentBinding.bind(view)

        val navController = findNavController()
        val savedStateHandle = navController.currentBackStackEntry?.savedStateHandle

        savedStateHandle?.getLiveData<PlaceUI>(PLACE)?.observe(viewLifecycleOwner) { result ->
            savedStateHandle.remove<PlaceUI>(PLACE)
            navController.previousBackStackEntry?.savedStateHandle?.set(PLACE, result)
            navController.popBackStack()
        }

        val favouriteCityAdapter = FavouriteCityAdapter(
            onItemClicked = { city ->
                navController.previousBackStackEntry?.savedStateHandle?.set(CITY_LOCATION, city)
                navController.popBackStack()
            },
            onLongItemClicked = { city ->
                val activity = activity as? AppCompatActivity
                val actionMode = activity?.startSupportActionMode(
                    CityActionMode(
                        onDelete = {
                            viewModel.deleteFavouriteCityFromDatabase(city)
                        }
                    )
                )
                actionMode?.title = city.name
            }
        )
        binding.favouriteCityList.adapter = favouriteCityAdapter

        binding.addNewCityButton.setOnClickListener {
            view.findNavController().navigate(
                FavouriteCityFragmentDirections.actionFavouriteCityFragmentToAddCityFragment()
            )
        }

        viewModel.cities.observe(viewLifecycleOwner) { cities ->
            favouriteCityAdapter.submitList(cities)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}