package com.example.weatherapp.ui.weather

import android.Manifest
import android.content.pm.PackageManager
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.annotation.RequiresPermission
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.weatherapp.R
import com.example.weatherapp.data.WeatherReport
import com.example.weatherapp.data.setWeatherImage
import com.example.weatherapp.network.Result
import com.example.weatherapp.databinding.FragmentWeatherBinding
import com.example.weatherapp.utils.formattedTime
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class WeatherFragment : Fragment(R.layout.fragment_weather) {
    private lateinit var viewModel: WeatherViewModel
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentWeatherBinding.bind(view)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        val dailyAdapter = DailyWeatherAdapter()
        binding.dailyWeather.adapter = dailyAdapter

        val hourlyAdapter = HourlyWeatherAdapter()
        binding.hourlyWeather.adapter = hourlyAdapter

        viewModel = ViewModelProvider(this)[WeatherViewModel::class.java]

        viewModel.response.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Result.Success -> handleSuccess(response, dailyAdapter, hourlyAdapter)
                is Result.Error -> handleError(response)
            }
        }

        val requestPermissionLauncher = createActivityResultLauncher()
        checkPermission(requestPermissionLauncher)
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
        binding.todayImage.setWeatherImage(iconId)
        binding.temperature.text = getString(
            R.string.temperature,
            current.temp.toInt().toString()
        )
        binding.description.text = current.weather.first().description
        binding.maxTemperature.text = getString(
            R.string.max_temperature,
            daily.first().temp.max.toInt().toString()
        )
        binding.minTemperature.text = getString(
            R.string.min_temperature,
            daily.first().temp.min.toInt().toString()
        )
        binding.sunriseTime.text = current.sunrise.formattedTime
        binding.sunsetTime.text = current.sunset.formattedTime
        binding.currentHumidity.text = getString(
            R.string.humidity,
            current.humidity.toString()
        )
        binding.currentPressure.text = getString(
            R.string.pressure,
            current.pressure.toString()
        )
        binding.currentVisibility.text = getString(
            R.string.visibility,
            current.visibility.toString()
        )
        binding.currentWindSpeed.text = getString(
            R.string.wind_speed,
            current.windSpeed.toString()
        )
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