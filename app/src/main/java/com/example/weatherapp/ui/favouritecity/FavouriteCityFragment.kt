package com.example.weatherapp.ui.favouritecity

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
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

        val favouriteCityAdapter = FavouriteCityAdapter(
            onItemClicked = { city ->
                // TODO handle item click
            },
            onLongItemClicked = { city ->
                val actionMode = (activity as? AppCompatActivity)?.startSupportActionMode(callback)
                actionMode?.title = city
            }
        )
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

    val callback = object : ActionMode.Callback {

        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            (activity as? AppCompatActivity)?.menuInflater?.inflate(
                R.menu.contextual_action_bar,
                menu
            )
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            return false
        }

        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
            return when (item?.itemId) {
                R.id.delete -> {
                    // Handle delete icon press
                    true
                }
                else -> false
            }
        }

        override fun onDestroyActionMode(mode: ActionMode?) {
        }
    }
}