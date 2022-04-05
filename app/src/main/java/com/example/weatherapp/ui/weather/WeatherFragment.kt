package com.example.weatherapp.ui.weather

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.annotation.RequiresPermission
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.weatherapp.R
import com.example.weatherapp.feature.fetchweather.data.WeatherReport
import com.example.weatherapp.data.setWeatherImage
import com.example.weatherapp.common.Result
import com.example.weatherapp.databinding.FragmentWeatherBinding
import com.example.weatherapp.utils.formattedTime
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint

class WeatherFragment : Fragment(R.layout.fragment_weather) {

    private val viewModel: WeatherViewModel by viewModels()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentWeatherBinding.bind(view)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        setupAppBar()

//        activity.setTitle()

        val dailyAdapter = DailyWeatherAdapter()
        binding.dailyWeatherContent.dailyWeather.adapter = dailyAdapter

        val hourlyAdapter = HourlyWeatherAdapter()
        binding.hourlyWeatherContent.hourlyWeather.adapter = hourlyAdapter

        viewModel.weatherReport.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Result.Success -> handleSuccess(response, dailyAdapter, hourlyAdapter)
                is Result.Error -> handleError(response)
            }
        }

        viewModel.cityName.observe(viewLifecycleOwner) { response ->
            binding.topAppBar.title = when (response) {
                is Result.Error -> getString(R.string.fetch_city_name_error)
                is Result.Success -> response.data
            }
        }

        val requestPermissionLauncher = createActivityResultLauncher()
        checkPermission(requestPermissionLauncher)
    }

    private fun setupAppBar() {
        val navController = findNavController()
        binding.topAppBar.setOnMenuItemClickListener { item ->
            NavigationUI.onNavDestinationSelected(item, navController)
        }
    }

    private fun handleError(response: Result.Error) {
        val message = response.message.toString()
        Log.d("Error", response.message.toString())
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    private fun handleSuccess(
        response: Result.Success<WeatherReport>,
        dailyAdapter: DailyWeatherAdapter,
        hourlyAdapter: HourlyWeatherAdapter
    ) {
        response.data.apply {
            dailyAdapter.submitList(daily)
            hourlyAdapter.submitList(hourly)
            bindCurrentWeather()
        }
    }

    private fun WeatherReport.bindCurrentWeather() {
        val iconId = current.weather.first().icon
        with(binding) {
            todayImage.setWeatherImage(iconId)
            temperature.text = getString(
                R.string.temperature,
                current.temp.toInt().toString()
            )
            description.text = current.weather.first().description

            temperatureContent.maxTemperature.text = getString(
                R.string.max_temperature,
                daily.first().temp.max.toInt().toString()
            )
            temperatureContent.minTemperature.text = getString(
                R.string.min_temperature,
                daily.first().temp.min.toInt().toString()
            )

            with(weatherDetailsContent) {
                sunriseTime.text = current.sunrise.formattedTime
                sunsetTime.text = current.sunset.formattedTime
                currentHumidity.text = getString(
                    R.string.humidity,
                    current.humidity.toString()
                )
                currentPressure.text = getString(
                    R.string.pressure,
                    current.pressure.toString()
                )
                currentVisibility.text = getString(
                    R.string.visibility,
                    current.visibility.toString()
                )
                currentWindSpeed.text = getString(
                    R.string.wind_speed,
                    current.windSpeed.toString()
                )
            }
        }
    }

    @RequiresPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
    private fun createActivityResultLauncher(): ActivityResultLauncher<String> {
        val requestPermissionLauncher = registerForActivityResult(
            RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                getLastLocation()
            } else {
                viewModel.fetchWeatherReport()
                viewModel.fetchCityName()
            }
        }
        return requestPermissionLauncher
    }

    private fun checkPermission(requestPermissionLauncher: ActivityResultLauncher<String>) {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                getLastLocation()
            }
            else -> requestPermissionLauncher.launch(
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        }
    }

    @RequiresPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
    private fun getLastLocation() {
        fusedLocationClient.lastLocation.addOnCompleteListener { location ->
            val result = location.result
            if (result != null) {
                viewModel.fetchWeatherReport(result.latitude, result.longitude)
                viewModel.fetchCityName(result.latitude, result.longitude)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}