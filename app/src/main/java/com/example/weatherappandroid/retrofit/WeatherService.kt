package com.example.weatherappandroid.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherService {
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /*private val retrofit = Retrofit.Builder()
        .baseUrl("https://openweathermap.org/data/2.5/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()*/
}