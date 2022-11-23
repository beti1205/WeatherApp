package com.example.weatherapp.ui.favouritecity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.FavouriteCityListItemBinding
import com.example.weatherapp.feature.storefavouritecities.data.FavouriteCity

class FavouriteCityAdapter(
    val onItemClicked: (FavouriteCity) -> Unit,
    val onLongItemClicked: (FavouriteCity) -> Unit
) : ListAdapter<FavouriteCity, FavouriteCityAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavouriteCityAdapter.ViewHolder {
        val binding = FavouriteCityListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val favouriteCity = getItem(position)
        viewHolder.bind(favouriteCity)
    }

    inner class ViewHolder(
        private val binding: FavouriteCityListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(favouriteCity: FavouriteCity) {
            binding.favouriteCity.text = favouriteCity.name
            binding.favouriteCity.setOnClickListener { onItemClicked(favouriteCity) }
            binding.favouriteCity.setOnLongClickListener {
                onLongItemClicked(favouriteCity)
                true
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<FavouriteCity>() {
        override fun areItemsTheSame(oldItem: FavouriteCity, newItem: FavouriteCity): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: FavouriteCity, newItem: FavouriteCity): Boolean {
            return oldItem.id == newItem.id
        }
    }
}
