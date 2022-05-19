package com.example.weatherapp.ui.addcity

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.R
import com.example.weatherapp.databinding.AddCityFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddCityFragment : Fragment(R.layout.add_city_fragment) {

    private val viewModel: AddCityViewModel by viewModels()
    private var _binding: AddCityFragmentBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val PLACE = "place"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = AddCityFragmentBinding.bind(view)

        val navController = findNavController()

        val addCityAdapter = AddCityAdapter(
            onItemClicked = { place ->
                viewModel.addFavouriteCityToDatabase(place)
                navController.previousBackStackEntry?.savedStateHandle?.set(PLACE, place)
                navController.popBackStack()
            }
        )

        binding.placeList.adapter = addCityAdapter
        binding.addCity.editText?.doAfterTextChanged { inputText ->
            viewModel.onCityNameChanged(inputText.toString())
        }

        viewModel.place.observe(viewLifecycleOwner) { response ->
            addCityAdapter.submitList(response)
        }

        viewModel.emptyState.observe(viewLifecycleOwner) { emptyState ->
            setEmptyStateVisibility(emptyState)
        }
    }

    private fun setEmptyStateVisibility(emptyStateType: EmptyStateType?) {
        val isEmptyStateVisible = emptyStateType != null
        binding.placeList.isVisible = !isEmptyStateVisible
        binding.emptyState.isVisible = isEmptyStateVisible
        binding.emptyStateMessage.isVisible = isEmptyStateVisible

        binding.emptyStateMessage.text = when (emptyStateType) {
            EmptyStateType.ERROR -> getString(R.string.add_city_empty_state_error)
            EmptyStateType.NO_RESULT -> getString(R.string.add_city_empty_state_no_results)
            EmptyStateType.SEARCH_CITY -> getString(R.string.add_city_empty_state_message)
            null -> null
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
