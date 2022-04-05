package com.example.weatherapp.ui.weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.databinding.HourlyWeatherListItemBinding
import com.example.weatherapp.feature.fetchweather.data.HourlyWeather
import com.example.weatherapp.utils.setWeatherImage
import com.example.weatherapp.utils.formattedTimeShort

class HourlyWeatherAdapter : ListAdapter<HourlyWeather, HourlyWeatherAdapter.ViewHolder>(
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

        fun bind(hourlyWeather: HourlyWeather) {
            val iconId = hourlyWeather.weather.first().icon
            binding.hourlyWeatherIcon.setWeatherImage(iconId)
            binding.time.text = hourlyWeather.time.formattedTimeShort
            binding.hourlyTemperature.text = binding.hourlyTemperature.context.getString(
                R.string.temperature,
                hourlyWeather.temp.toInt().toString()
            )
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<HourlyWeather>() {
        override fun areItemsTheSame(oldItem: HourlyWeather, newItem: HourlyWeather): Boolean {
            return oldItem.time == newItem.time
        }

        override fun areContentsTheSame(oldItem: HourlyWeather, newItem: HourlyWeather): Boolean {
            return oldItem == newItem
        }

    }
}
