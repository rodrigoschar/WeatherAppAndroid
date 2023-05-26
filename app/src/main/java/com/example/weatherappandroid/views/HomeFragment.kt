package com.example.weatherappandroid.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherappandroid.R
import com.example.weatherappandroid.adapters.WeatherAdapter
import com.example.weatherappandroid.databinding.FragmentHomeBinding
import com.example.weatherappandroid.models.list
import com.example.weatherappandroid.retrofit.WeatherApi
import com.example.weatherappandroid.retrofit.WeatherService
import com.example.weatherappandroid.utils.CustomProgressDialog
import com.example.weatherappandroid.utils.Utils
import kotlinx.coroutines.*

class HomeFragment : Fragment(), WeatherAdapter.IWeatherListener {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val call = WeatherService()
    private val progressDialog by lazy { CustomProgressDialog(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecycler()

        binding.svWeather.setBackgroundResource(R.drawable.item_corners)
        binding.svWeather.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return if (query.isNotEmpty()) {
                    progressDialog.start("Searching city...")
                    GlobalScope.launch(Dispatchers.IO) {
                        searchByCityName(query)
                    }
                    true
                } else {
                    Toast.makeText(binding.root.context, "Please write a city name to start searching", Toast.LENGTH_LONG).show()
                    false
                }
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }

    private suspend fun searchByCityName(cityName: String) {
        withContext(Dispatchers.IO) {
            try {
                val url = "find?q=${cityName}&appid=${Utils.appId}&units=metric"
                val callApi = call.getRetrofit().create(WeatherApi::class.java).getWeatherByCityName(url).execute()
                val result = callApi.body()
                if (result != null) {
                    withContext(Dispatchers.Main) {
                        (binding.rvCities.adapter as WeatherAdapter).updateList(result.list.toMutableList())
                        progressDialog.stop()
                    }
                } else {
                    Toast.makeText(binding.root.context, "Error write a correct city name", Toast.LENGTH_LONG).show()
                    Log.d("TAG", "is Null")
                }
            } catch (ex: Exception) {
                Log.d("TAG", ex.printStackTrace().toString())
            }
        }
    }

    private fun setRecycler() {
        binding.rvCities.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCities.adapter = WeatherAdapter(mutableListOf(), this)
    }

    override fun onItemTap(weather: list) {
        val action = HomeFragmentDirections.actionHomeFragmentToWeatherDetailFragment(weather)
        findNavController().navigate(action)
    }
}