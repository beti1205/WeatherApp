package com.example.weatherapp.ui.weather

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.annotation.RequiresPermission
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import com.example.weatherapp.R
import com.example.weatherapp.common.Result
import com.example.weatherapp.databinding.WeatherFragmentBinding
import com.example.weatherapp.feature.fetchplacebyname.ui.PlaceUI
import com.example.weatherapp.feature.fetchweather.ui.WeatherReportUI
import com.example.weatherapp.feature.storefavouritecities.data.FavouriteCity
import com.example.weatherapp.ui.favouritecity.FavouriteCityFragment
import com.example.weatherapp.utils.setWeatherImage
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherFragment : Fragment(R.layout.weather_fragment) {

    private val viewModel: WeatherViewModel by viewModels()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var _binding: WeatherFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = WeatherFragmentBinding.bind(view)
        setHasOptionsMenu(true)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

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

        viewModel.cityName.observe(viewLifecycleOwner) { cityName ->
            val actionBar = (activity as? AppCompatActivity)?.supportActionBar
            actionBar?.title = cityName ?: getString(R.string.fetch_city_name_error)
        }

        val navController = findNavController()
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressIndicator.isVisible = isLoading
            binding.contentScrollView.isVisible = !isLoading
        }

        val savedStateHandle = navController.currentBackStackEntry?.savedStateHandle
        savedStateHandle?.getLiveData<FavouriteCity>(FavouriteCityFragment.CITY_LOCATION)
            ?.observe(viewLifecycleOwner) { result ->
                savedStateHandle.remove<FavouriteCity>(FavouriteCityFragment.CITY_LOCATION)
                viewModel.fetchWeatherReport(
                    latitude = result.latitude,
                    longitude = result.longitude,
                    force = true
                )
                viewModel.setCityName(result.name)
            }

        savedStateHandle?.getLiveData<PlaceUI>(FavouriteCityFragment.PLACE)
            ?.observe(viewLifecycleOwner) { result ->
                savedStateHandle.remove<PlaceUI>(FavouriteCityFragment.PLACE)
                viewModel.fetchWeatherReport(
                    latitude = result.latitude,
                    longitude = result.longitude,
                    force = true
                )
                viewModel.setCityName(result.properties.name)
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
        response: Result.Success<WeatherReportUI>,
        dailyAdapter: DailyWeatherAdapter,
        hourlyAdapter: HourlyWeatherAdapter
    ) {
        response.data.apply {
            dailyAdapter.submitList(daily)
            hourlyAdapter.submitList(hourly)
            bindCurrentWeather()
        }
    }

    private fun WeatherReportUI.bindCurrentWeather() {
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
                sunriseTime.text = current.sunrise
                sunsetTime.text = current.sunset
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
            val result: Location? = location.result
            viewModel.fetchWeatherReport(result?.latitude, result?.longitude)
            viewModel.fetchCityName(result?.latitude, result?.longitude)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.weather_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = findNavController()
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}