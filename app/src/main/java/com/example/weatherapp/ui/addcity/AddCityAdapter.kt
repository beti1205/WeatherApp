package com.example.weatherapp.ui.addcity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.databinding.AddCityListItemBinding
import com.example.weatherapp.feature.fetchplacebyname.ui.PlaceUI

class AddCityAdapter(
    val onItemClicked: (PlaceUI) -> Unit
) : ListAdapter<PlaceUI, AddCityAdapter.ViewHolder>(DiffCallback) {

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

        fun bind(place: PlaceUI) {
            with(binding) {
                val context = country.context
                root.setOnClickListener { onItemClicked(place) }
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

    companion object DiffCallback : DiffUtil.ItemCallback<PlaceUI>() {
        override fun areItemsTheSame(oldItem: PlaceUI, newItem: PlaceUI): Boolean {
            return oldItem.properties.id == newItem.properties.id
        }

        override fun areContentsTheSame(oldItem: PlaceUI, newItem: PlaceUI): Boolean {
            return oldItem == newItem
        }
    }
}