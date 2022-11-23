package com.example.weatherapp.ui.weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.databinding.HourlyWeatherListItemBinding
import com.example.weatherapp.feature.fetchweather.ui.HourlyWeatherUI
import com.example.weatherapp.utils.setWeatherImage

class HourlyWeatherAdapter : ListAdapter<HourlyWeatherUI, HourlyWeatherAdapter.ViewHolder>(
    DiffCallback
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = HourlyWeatherListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val hourlyWeather = getItem(position)
        holder.bind(hourlyWeather)
    }

    inner class ViewHolder(
        private val binding: HourlyWeatherListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(hourlyWeather: HourlyWeatherUI) {
            val iconId = hourlyWeather.weather.first().icon
            binding.hourlyWeatherIcon.setWeatherImage(iconId)
            binding.time.text = hourlyWeather.time
            binding.hourlyTemperature.text = binding.hourlyTemperature.context.getString(
                R.string.temperature,
                hourlyWeather.temp.toInt().toString()
            )
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<HourlyWeatherUI>() {
        override fun areItemsTheSame(oldItem: HourlyWeatherUI, newItem: HourlyWeatherUI): Boolean {
            return oldItem.timeStamp == newItem.timeStamp
        }

        override fun areContentsTheSame(
            oldItem: HourlyWeatherUI,
            newItem: HourlyWeatherUI
        ): Boolean {
            return oldItem == newItem
        }
    }
}
