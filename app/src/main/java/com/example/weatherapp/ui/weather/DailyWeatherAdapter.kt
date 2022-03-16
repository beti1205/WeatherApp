package com.example.weatherapp.ui.weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapp.data.DailyWeather
import com.example.weatherapp.data.setWeatherImage
import com.example.weatherapp.databinding.ListItemDailyWeatherBinding
import java.text.SimpleDateFormat
import java.util.*

class DailyWeatherAdapter :
    ListAdapter<DailyWeather, DailyWeatherAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemDailyWeatherBinding.inflate(
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
        private val binding: ListItemDailyWeatherBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private val dateFormat = SimpleDateFormat.getDateInstance()
        private val dayFormat = SimpleDateFormat("E", Locale.getDefault())

        fun bind(dailyWeather: DailyWeather) {
            val iconId = dailyWeather.weather.joinToString { it.icon }
            binding.dailyWeatherIcon.setWeatherImage(iconId)
            binding.date.text = dateFormat.format(dailyWeather.time)
            binding.day.text = dayFormat.format(dailyWeather.time)
            binding.dailyWeatherDescription.text = dailyWeather.weather.joinToString { weather ->
                weather.description
            }
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