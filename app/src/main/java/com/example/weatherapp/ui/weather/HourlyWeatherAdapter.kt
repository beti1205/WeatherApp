package com.example.weatherapp.ui.weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapp.R
import com.example.weatherapp.data.HourlyWeather
import com.example.weatherapp.data.setWeatherImage
import com.example.weatherapp.databinding.ListItemHourlyWeatherBinding
import java.text.SimpleDateFormat
import java.util.*

class HourlyWeatherAdapter : ListAdapter<HourlyWeather, HourlyWeatherAdapter.ViewHolder>(
    DiffCallback
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemHourlyWeatherBinding.inflate(
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
        private val binding: ListItemHourlyWeatherBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private val simpleDateFormat = SimpleDateFormat("hh:mm a",Locale.getDefault())

        fun bind(hourlyWeather: HourlyWeather) {

            val iconId = hourlyWeather.weather.joinToString { it.icon }
//            val imageUri = "https://openweathermap.org/img/wn/$iconId@2x.png"

            binding.hourlyWeatherIcon.setWeatherImage(iconId)

//            Glide.with(binding.hourlyWeatherIcon.context)
//                .load(imageUri)
//                .into(binding.hourlyWeatherIcon)

            val hourFormat = simpleDateFormat.format(hourlyWeather.time)
            binding.time.text = hourFormat.toString()
            binding.hourlyTemperature.text = binding.hourlyTemperature.context.getString(
                R.string.temperature,
                hourlyWeather.temp.toInt().toString()
            )
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<HourlyWeather>() {
        override fun areItemsTheSame(oldItem: HourlyWeather, newItem: HourlyWeather): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: HourlyWeather, newItem: HourlyWeather): Boolean {
            return oldItem.temp == newItem.temp
        }

    }
}
