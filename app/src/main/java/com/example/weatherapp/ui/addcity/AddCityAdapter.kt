package com.example.weatherapp.ui.addcity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import com.example.weatherapp.R
import com.example.weatherapp.databinding.AddCityListItemBinding
import com.example.weatherapp.feature.fetchplacebyname.data.Place


class AddCityAdapter : ListAdapter<Place, AddCityAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AddCityAdapter.ViewHolder {
        val binding = AddCityListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val place = getItem(position)
        viewHolder.bind(place)
    }

    inner class ViewHolder(
        private val binding: AddCityListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(place: Place) {
            with(binding) {
                val context = country.context

                city.text = place.properties.name
                country.text = when {
                    place.properties.county != null -> context.getString(
                        R.string.city_localization,
                        place.properties.county,
                        place.properties.country
                    )
                    else -> place.properties.country
                }
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Place>() {
        override fun areItemsTheSame(oldItem: Place, newItem: Place): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Place, newItem: Place): Boolean {
            return oldItem.geometry == newItem.geometry
        }
    }
}