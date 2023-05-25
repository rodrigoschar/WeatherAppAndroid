package com.example.weatherappandroid.views

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.weatherappandroid.R
import com.example.weatherappandroid.databinding.FragmentWeatherDetailBinding
import com.example.weatherappandroid.models.list
import com.example.weatherappandroid.utils.Utils
import com.squareup.picasso.Picasso
import okhttp3.internal.Util

class WeatherDetailFragment : Fragment() {
    private var _binding: FragmentWeatherDetailBinding? = null
    private val binding get() = _binding!!
    private var weatherInfo: list? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments.let {
            weatherInfo = it?.let { it1 -> WeatherDetailFragmentArgs.fromBundle(it1).detail }
            weatherInfo?.let { it2 -> updateUI(it2) }
        }
    }

    private fun updateUI(data: list) {
        Picasso.with(binding.root.context).load(Utils.getWeatherImage(data.weather.first().icon)).resize(50, 50).centerCrop()
            .into(binding.imvWeather)
        Picasso.with(binding.root.context).load(Utils.getFlagImage(data.sys.country)).resize(50, 50).centerCrop()
            .into(binding.imvFlag)
        binding.tvCountry.text = Utils.setFirstTextBold(binding.root.context.resources.getString(R.string.country), data.sys.country)
        binding.tvCity.text = Utils.setFirstTextBold(binding.root.context.resources.getString(R.string.city_name), data.name)
        binding.tvCurrentWeather.text = Utils.setFirstTextBold(binding.root.context.resources.getString(R.string.current_weather), data.weather.first().main + " , " + data.weather.first().description)
        binding.tvCurrentTemp.text = Utils.setFirstTextBold(binding.root.context.resources.getString(R.string.current_temp), Utils.convertToCelsius(data.main.temp))
        binding.tvCloudCoverage.text = Utils.setFirstTextBold(binding.root.context.resources.getString(R.string.clouds_coverage), "${data.clouds?.all}%")
        binding.tvWindSpeed.text = Utils.setFirstTextBold(binding.root.context.resources.getString(R.string.wind_speed), "${data.wind.speed} mph")
        binding.tvMinTemp.text = Utils.setFirstTextBold(binding.root.context.resources.getString(R.string.min_temp), Utils.convertToCelsius(data.main.tempMin))
        binding.tvMaxTemp.text = Utils.setFirstTextBold(binding.root.context.resources.getString(R.string.max_temp), Utils.convertToCelsius(data.main.tempMax))
    }
}