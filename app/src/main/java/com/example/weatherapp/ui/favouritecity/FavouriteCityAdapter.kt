package com.example.weatherapp.ui.favouritecity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.ListItemFavouriteCityBinding

class FavouriteCityAdapter : RecyclerView.Adapter<FavouriteCityAdapter.ViewHolder>() {

    val data: List<String> = listOf("Warszawa", "Kraków", "Siedlce")

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavouriteCityAdapter.ViewHolder {
        val binding = ListItemFavouriteCityBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val favouriteCity = data[position]
        viewHolder.bind(favouriteCity)
    }

    override fun getItemCount() = data.size

    inner class ViewHolder(
        private val binding: ListItemFavouriteCityBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(favouriteCity: String){
            binding.favouriteCity.text = favouriteCity
        }
    }
}

