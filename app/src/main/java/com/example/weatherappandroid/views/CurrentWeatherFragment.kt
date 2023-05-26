package com.example.weatherappandroid.views

import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.weatherappandroid.R
import com.example.weatherappandroid.databinding.FragmentCurrentWeatherBinding
import com.example.weatherappandroid.models.CurrentWeatherModel
import com.example.weatherappandroid.retrofit.WeatherApi
import com.example.weatherappandroid.retrofit.WeatherService
import com.example.weatherappandroid.utils.CustomProgressDialog
import com.example.weatherappandroid.utils.Utils
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class CurrentWeatherFragment : Fragment() {
    private var _binding: FragmentCurrentWeatherBinding? = null
    private val binding get() = _binding!!
    private val call = WeatherService()
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private val progressDialog by lazy { CustomProgressDialog(requireContext()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCurrentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            checkLocationPermission()
        }
    }

    private suspend fun checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(binding.root.context, android.Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(binding.root.context, android.Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 101)
            return
        } else {
            mFusedLocationClient.lastLocation.addOnCompleteListener {
                progressDialog.start("Loading Data...")
                GlobalScope.launch(Dispatchers.IO) {
                    searchWeatherByLocation(it.result.latitude, it.result.longitude)
                }
            }
        }
    }

    private suspend fun searchWeatherByLocation(latitude: Double, longitude: Double) {
        try {
            val url = Utils.getCurrentWeatherUrl(latitude, longitude)
            val callApi = call.getRetrofit().create(WeatherApi::class.java).getCurrentWeather(url).execute()
            val result = callApi.body()
            if (result != null) {
                withContext(Dispatchers.Main) {
                    updateUI(result)
                }
            } else {
                Toast.makeText(binding.root.context, "Error trying to get the Location", Toast.LENGTH_LONG).show()
                Log.d("TAG", "is Null")
            }
        } catch (ex: Exception) {
            Log.d("TAG", ex.printStackTrace().toString())
        }
    }

    private fun updateUI(data: CurrentWeatherModel) {
        Picasso.with(binding.root.context).load(Utils.getWeatherImage(data.weather.first().icon)).resize(50, 50).centerCrop()
            .into(binding.imvWeather)
        binding.tvLocation.text = Utils.setFirstTextBold(binding.root.context.resources.getString(R.string.location), data.name + " , " + data.sys.country)
        binding.tvCurrentWeather.text = Utils.setFirstTextBold(binding.root.context.resources.getString(
            R.string.current_weather), data.weather.first().main + " , " + data.weather.first().description)
        binding.tvCurrentTemp.text = Utils.setFirstTextBold(binding.root.context.resources.getString(
            R.string.current_temp), Utils.convertToCelsius(data.main.temp))
        binding.tvCloudCoverage.text = Utils.setFirstTextBold(binding.root.context.resources.getString(
            R.string.clouds_coverage), "${data.clouds.all}%")
        binding.tvWindSpeed.text = Utils.setFirstTextBold(binding.root.context.resources.getString(R.string.wind_speed), "${data.wind.speed} mph")
        binding.tvMinTemp.text = Utils.setFirstTextBold(binding.root.context.resources.getString(R.string.min_temp), Utils.convertToCelsius(data.main.tempMin))
        binding.tvMaxTemp.text = Utils.setFirstTextBold(binding.root.context.resources.getString(R.string.max_temp), Utils.convertToCelsius(data.main.tempMax))
        binding.tvLatitude.text = Utils.setFirstTextBold(binding.root.context.resources.getString(R.string.latitude), data.coord.lat.toString())
        binding.tvLongitude.text = Utils.setFirstTextBold(binding.root.context.resources.getString(R.string.longitude), data.coord.lon.toString())
        progressDialog.stop()
    }
}