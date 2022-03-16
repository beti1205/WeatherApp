package com.example.weatherapp.ui.weather

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.weatherapp.R
import com.example.weatherapp.data.setWeatherImage
import com.example.weatherapp.network.Result
import com.example.weatherapp.databinding.FragmentWeatherBinding
import java.text.SimpleDateFormat

class WeatherFragment : Fragment() {

    companion object {
        fun newInstance() = WeatherFragment()
    }

    private lateinit var viewModel: WeatherViewModel
    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = DailyWeatherAdapter()
        binding.dailyWeather.adapter = adapter

        val hourAdapter = HourlyWeatherAdapter()
        binding.hourlyWeather.adapter = hourAdapter

        viewModel = ViewModelProvider(this)[WeatherViewModel::class.java]

        viewModel.response.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Result.Success -> {
                    response.data.apply {
                        binding.temperature.text =
                            getString(R.string.temperature, current.temp.toInt().toString())
                        binding.description.text = current.weather.joinToString { it.description }
                        binding.minTemperature.text = getString(
                            R.string.min_temperature,
                            daily.first().temp.min.toInt().toString()
                        )
                        binding.maxTemperature.text = getString(
                            R.string.max_temperature,
                            daily.first().temp.max.toInt().toString()
                        )
                        val simpleDateFormat = SimpleDateFormat.getTimeInstance()
                        binding.dailySunrise.text = simpleDateFormat.format(current.sunrise)
                        binding.dailySunset.text = simpleDateFormat.format(current.sunset)
                        binding.currentHumidity.text =
                            getString(R.string.humidity, current.humidity.toString())
                        binding.currentPressure.text =
                            getString(R.string.pressure, current.pressure.toString())
                        binding.currentVisibility.text =
                            getString(R.string.visibility, current.visibility.toString())
                        binding.currentWindSpeed.text =
                            getString(R.string.wind_speed, current.windSpeed.toString())

                        val iconId = current.weather.joinToString { it.icon }
                        binding.todayImage.setWeatherImage(iconId)
//                        val imageUri = "https://openweathermap.org/img/wn/$iconId@2x.png"
//
//                        Glide.with(binding.todayImage.context)
//                            .load(imageUri)
//                            .into(binding.todayImage)

                        adapter.submitList(response.data.daily)
                        hourAdapter.submitList(response.data.hourly)
                    }
                }
                is Result.Error -> {
                    val message = response.message.toString()
                    Log.d("Error", response.message.toString())
                    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                }
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_app_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.change -> {
                TODO()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}