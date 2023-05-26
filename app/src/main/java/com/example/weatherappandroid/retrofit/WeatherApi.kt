package com.example.weatherappandroid.retrofit

import com.example.weatherappandroid.models.CurrentWeatherModel
import com.example.weatherappandroid.models.WeatherModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface WeatherApi {
    @GET
    fun getWeatherByCityName(@Url url: String): Call<WeatherModel>

    @GET()
    fun getCurrentWeather(@Url url: String): Call<CurrentWeatherModel>
}