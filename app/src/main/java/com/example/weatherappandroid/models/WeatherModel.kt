package com.example.weatherappandroid.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class WeatherModel(
    @SerializedName("message")
    var message: String,
    @SerializedName("cod")
    var cod: String,
    @SerializedName("count")
    var count: Int,
    @SerializedName("list")
    var list: List<list>,
)

data class list (
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("coord")
    var coord: Coord,
    @SerializedName("main")
    var main: Main,
    @SerializedName("dt")
    var dt: Int,
    @SerializedName("wind")
    var wind: Wind,
    @SerializedName("sys")
    var sys: Sys,
    @SerializedName("rain")
    var rain: Rain?,
    @SerializedName("snow")
    var snow: Snow?,
    @SerializedName("clouds")
    var clouds: Clouds?,
    @SerializedName("weather")
    var weather: List<Weather>
) : Serializable

data class Coord(
    @SerializedName("lat")
    var lat: Double,
    @SerializedName("lon")
    var lon: Double
)

data class Main (
    @SerializedName("temp")
    var temp: Double,
    @SerializedName("feels_like")
    var feelsLike: Double,
    @SerializedName("temp_min")
    var tempMin: Double,
    @SerializedName("temp_max")
    var tempMax: Double,
    @SerializedName("pressure")
    var pressure: Int,
    @SerializedName("humidity")
    var humidity: Int,
    @SerializedName("sea_level")
    var seaLevel: Int,
    @SerializedName("grnd_level")
    var grndLevel: Int
)

data class Wind(
    @SerializedName("speed")
    var speed: Double,
    @SerializedName("deg")
    var deg: Int
)

data class Sys(
    @SerializedName("country")
    var country: String
)

data class Rain(
    @SerializedName("h1")
    var h1: Double,
    @SerializedName("h3")
    var h3: Double
)

data class Snow(
    @SerializedName("h1")
    var h1: Double,
    @SerializedName("h3")
    var h3: Double
)

data class Clouds(
    @SerializedName("all")
    var all: Int
)

data class Weather(
    @SerializedName("id")
    var id: Int,
    @SerializedName("main")
    var main: String,
    @SerializedName("description")
    var description: String,
    @SerializedName("icon")
    var icon: String
)
