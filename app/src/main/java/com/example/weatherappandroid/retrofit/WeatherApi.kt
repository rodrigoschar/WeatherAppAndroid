package com.example.weatherappandroid.retrofit

import com.example.weatherappandroid.models.CurrentWeatherModel
import com.example.weatherappandroid.models.WeatherModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface WeatherApi {
    @GET()
    fun getWeatherByCityName(@Url url: String): Call<WeatherModel>

    @GET("weather?lat={lat}&lon={lon}&appid=ec6d4b2f3e345a4df28f7eec09ae0144")
    fun getCurrentWeather(@Query("lat") lat: Double, @Query("lon") lon: Double): Call<CurrentWeatherModel>
}