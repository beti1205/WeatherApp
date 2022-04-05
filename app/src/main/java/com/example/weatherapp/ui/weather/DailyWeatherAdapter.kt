package com.example.weatherapp.ui.weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.databinding.DailyWeatherListItemBinding
import com.example.weatherapp.feature.fetchweather.data.DailyWeather
import com.example.weatherapp.utils.setWeatherImage
import com.example.weatherapp.utils.formattedDate
import com.example.weatherapp.utils.formattedDayName

class DailyWeatherAdapter :
    ListAdapter<DailyWeather, DailyWeatherAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DailyWeatherListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dailyWeather = getItem(position)
        holder.bind(dailyWeather)
    }

    inner class ViewHolder(
        private val binding: DailyWeatherListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(dailyWeather: DailyWeather) {
            val iconId = dailyWeather.weather.first().icon
            binding.dailyWeatherIcon.setWeatherImage(iconId)
            binding.date.text = dailyWeather.time.formattedDate
            binding.day.text = dailyWeather.time.formattedDayName
            binding.dailyWeatherDescription.text = dailyWeather.weather.first().description
            binding.dailyTemperature.text = binding.dailyTemperature.context.getString(
                R.string.temperature,
                dailyWeather.temp.max.toInt().toString()
            )
        }

    }

    companion object DiffCallback : DiffUtil.ItemCallback<DailyWeather>() {
        override fun areItemsTheSame(oldItem: DailyWeather, newItem: DailyWeather): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: DailyWeather, newItem: DailyWeather): Boolean {
            return oldItem.temp == newItem.temp
        }

    }
}